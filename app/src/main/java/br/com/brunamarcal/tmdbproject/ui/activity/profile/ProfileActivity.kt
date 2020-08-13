package br.com.brunamarcal.tmdbproject.ui.activity.profile

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.data.util.SharedPreference
import br.com.brunamarcal.tmdbproject.ui.activity.login.LoginActivity
import br.com.brunamarcal.tmdbproject.ui.activity.profile.viewmodel.ProfileViewModel
import br.com.brunamarcal.tmdbproject.ui.activity.register.RegisterActivity
import br.com.brunamarcal.tmdbproject.ui.adapter.ProfileAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers

class ProfileActivity : AppCompatActivity() {
    lateinit var profileAdapter: ProfileAdapter
    lateinit var alert: AlertDialog
    lateinit var viewModel: ProfileViewModel
    private var getUserId: Long = 0
    lateinit var mUser: User
    lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        toolbarMain.title = ""
        setSupportActionBar(toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        logout()

        val repository = Repository(this)
        viewModel = ProfileViewModel.ProfileViewModelProviderFactory(application, repository, Dispatchers.IO).create(ProfileViewModel::class.java)

        sharedPreference = SharedPreference(this)
        sharedPreference.getData(LoginActivity.USER)?.let {
            getUserId = it
        }
        sharedPreference.getSavedImage(getUserId.toString())?.let {
            if (it != 0){
                Picasso.get().load(it).into(imgProfile)
            }
        }

        changeImage(sharedPreference)

        viewModel.getUser(getUserId).observe(this, Observer {
            it?.let { user ->
                mUser = user
                txtUserNameProfile.text = user.name
                txtUserEmailProfile.text = user.email
                txtuserPasswordProfile.text = user.password
            }
        })

        imgConfigUser.setOnClickListener {
            setupBottomSheet()
        }
    }

    private fun logout() {
        logout.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupBottomSheet() {
        val bottomSheet = BottomSheetDialog(this@ProfileActivity)
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val deleteAccount = view.findViewById<TextView>(R.id.txtDeleteAccount)
        val editProfile = view.findViewById<TextView>(R.id.txtEditProfile)
        bottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomSheet.setContentView(view)
        bottomSheet.show()


        editProfile.setOnClickListener {
            val intent = Intent(this@ProfileActivity, RegisterActivity::class.java)
            intent.putExtra("USER_REPLACE", mUser)
            startActivity(intent)
            finish()
        }

        deleteAccount.setOnClickListener {
            setupAlertDialog(bottomSheet)
        }
    }

    private fun setupAlertDialog(bottomSheet: BottomSheetDialog) {
        val builder = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.custom_dialog, null)
        val btnYes = view.findViewById(R.id.btnYes) as Button
        val btnNo = view.findViewById(R.id.btnNo) as Button

        btnYes.setOnClickListener {
            viewModel.deleteUser(mUser)
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
        btnNo.setOnClickListener {
            alert.dismiss()
            bottomSheet.dismiss()
        }

        builder.setView(view)
        alert = builder.create()
        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.show()
    }

    fun imageList() = listOf(
        R.drawable.anna, R.drawable.astride,
        R.drawable.banguela, R.drawable.bart,
        R.drawable.elza, R.drawable.homer,
        R.drawable.minion, R.drawable.minion_girl,
        R.drawable.comic_book_guy, R.drawable.olaf,
        R.drawable.lisa, R.drawable.soluco
    )

    private fun setupAlertDialogProfile(sharedPreference: SharedPreference) {
        val builder = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.custom_dialog_profile, null)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerProfile)
        profileAdapter = ProfileAdapter(imageList()){
            Picasso.get().load(it).into(imgProfile)
            sharedPreference.savedImage(getUserId.toString(), it)
            alert.dismiss()
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
            setHasFixedSize(true)
            adapter = profileAdapter
        }

        builder.setView(view)
        alert = builder.create()
        alert.show()
    }

    private fun changeImage(sharedPreference: SharedPreference) {
        imgEdit.setOnClickListener {
            setupAlertDialogProfile(sharedPreference)
        }

    }
}
