package br.com.brunamarcal.tmdbproject.ui.activity.profile

import android.app.AlertDialog
import android.content.Entity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.data.util.SharedPreference
import br.com.brunamarcal.tmdbproject.ui.activity.login.LoginActivity
import br.com.brunamarcal.tmdbproject.ui.activity.profile.viewmodel.ProfileViewModel
import br.com.brunamarcal.tmdbproject.ui.activity.register.RegisterActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers
import java.io.Serializable

class ProfileActivity : AppCompatActivity() {
    lateinit var alert: AlertDialog
    lateinit var viewModel: ProfileViewModel
    private var getUserId: Long = 0
    lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        toolbarMain.title = ""
        setSupportActionBar(toolbarMain)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        logout.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }

        val repository = Repository(this)
        viewModel = ProfileViewModel.ProfileViewModelProviderFactory(
            application,
            repository,
            Dispatchers.IO
        ).create(ProfileViewModel::class.java)

        val sharedPreference = SharedPreference(this)
        sharedPreference.getData(LoginActivity.USER)?.let {
            getUserId = it
        }

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
}

