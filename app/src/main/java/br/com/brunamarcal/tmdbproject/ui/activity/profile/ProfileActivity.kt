package br.com.brunamarcal.tmdbproject.ui.activity.profile

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.brunamarcal.tmdbproject.R
import br.com.brunamarcal.tmdbproject.data.database.modeldb.User
import br.com.brunamarcal.tmdbproject.data.repository.Repository
import br.com.brunamarcal.tmdbproject.data.util.SharedPreference
import br.com.brunamarcal.tmdbproject.ui.activity.login.LoginActivity
import br.com.brunamarcal.tmdbproject.ui.activity.profile.viewmodel.ProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers

class ProfileActivity : AppCompatActivity() {
    lateinit var alert: AlertDialog
    lateinit var viewModel: ProfileViewModel
    lateinit var getUserEmail: String
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
            getUserEmail = it
        }

        viewModel.getUser(getUserEmail).observe(this, Observer {
            it?.let { user ->
                mUser = user
                txtUserNameProfile.text = user.name
                txtUserEmailProfile.text = user.email
                txtuserPasswordProfile.text = user.password
            }
        })

        imgConfigUser.setOnClickListener {
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(this@ProfileActivity)
            val view: View = layoutInflater.inflate(R.layout.bottom_sheet, null)

            bottomSheet.setContentView(view)
            bottomSheet.show()

        }

    }


}



//        imgDeleteUser.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//
//            val view: View = layoutInflater.inflate(R.layout.custom_dialog, null)
//
//            val btnYes = view.findViewById(R.id.btnYes) as Button
//            val btnNo = view.findViewById(R.id.btnNo) as Button
//
//            btnYes.setOnClickListener {
//                viewModel.deleteUser(mUser)
//                startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
//                finish()
//            }
//            btnNo.setOnClickListener {
//                alert.dismiss()
//            }
//
//            builder.setView(view)
//            alert = builder.create()
//            alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            alert.show()
//
//        }
//    }
//}


