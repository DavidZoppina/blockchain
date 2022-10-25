package com.andtv.flicknplay.workbrowse.presentation.ui.fragment


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.andtv.flicknplay.data.Constants
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.data.remote.api.UserApi
import com.andtv.flicknplay.workbrowse.data.remote.model.ErrorResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.remote.model.login.LoginResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.register.RegisterRequestModel
import com.andtv.flicknplay.workbrowse.data.remote.model.register.RegisterResponse
import com.andtv.flicknplay.workbrowse.databinding.FragmentLoginBinding
import com.andtv.flicknplay.workbrowse.databinding.FragmentRegisterBinding
import com.andtv.flicknplay.workbrowse.helper.SharedPreferencesHelper
import com.andtv.flicknplay.workbrowse.presentation.presenter.RegisterPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.DeviceLoginButton
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder
import java.util.*
import javax.inject.Inject


class RegisterFragment : Fragment(), RegisterPresenter.View , FacebookLoginSDK.FacebookLoginResponse{
    private lateinit var editUsername: EditText
    private lateinit var editPassword: EditText
    private lateinit var confirmationPassword: EditText

    private lateinit var btnRegister: Button
    private lateinit var facebookBtn : LoginButton
    private lateinit var facebookLoginSDK:FacebookLoginSDK
    private lateinit var callbackManager : CallbackManager
    private lateinit var deviceLoginButton: DeviceLoginButton
//    private lateinit var deviceLoginManager: DeviceLoginManager
    private lateinit var loadingProgressBar: ProgressBar
    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient : GoogleSignInClient
    @Inject
    lateinit var presenter: RegisterPresenter

    lateinit var binding: FragmentRegisterBinding

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivityComponent
            .registerFragmentComponent()
            .create(this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindTo(this.lifecycle)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentRegisterBinding.inflate(inflater)
        editUsername = view.findViewById(R.id.username)
        editPassword = view.findViewById(R.id.password)
        facebookBtn = view.findViewById(R.id.facebook_btn)
        confirmationPassword = view.findViewById(R.id.confirmation_password)
        btnRegister = view.findViewById(R.id.button_register)
        deviceLoginButton = view.findViewById(R.id.device_login_btn)
        loadingProgressBar = view.findViewById(R.id.loading)

        callbackManager = CallbackManager.Factory.create()


//        deviceLoginManager = DeviceLoginManager()
        val gso =  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1018650312961-esergp9t1cpe02iuruqqbqk1sqrp5h4f.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)
        auth = Firebase.auth

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {

        }
        binding.loginBtn.setOnClickListener {
            hideKeyboard(it)
            goToLoginFragment()
        }
        btnRegister.setOnClickListener {
            hideKeyboard(it)
            startSignUp()
        }

        binding.googleLoginBtn.setOnClickListener{
            hideKeyboard(it)
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, MainActivity.RC_SIGN_IN)
            //Toast.makeText(context,(activity as MainActivity).auth.currentUser?.email.toString(),Toast.LENGTH_LONG).show()

        }



        /** Custom Facebook SDK */
        facebookLoginSDK = FacebookLoginSDK(requireActivity())
        facebookLoginSDK.facebookLoginResponse = this
        binding.facebookLoginBtn.setOnClickListener{
            onShowProgress()
            facebookLoginSDK.genrateToken()

        }


//        facebookBtn.setPermissions(Arrays.asList("email"))
//
//        facebookBtn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//            override fun onSuccess(loginResult: LoginResult) {
//
//                val request = GraphRequest.newMeRequest(
//                    loginResult.accessToken
//                ) { _object, response ->
//
//                    presenter.googleRegister("facebook",
//                        UserApi.REGISTER_SOCIAL_ACCOUNT,SocialLoginModel(
//                        _object?.getString("id"),
//                        _object?.getString("name"),
//                        _object?.getString("email"),
//                        "",
//                        "" ,
//                        loginResult.accessToken.token,
//                        "",
//                        loginResult.accessToken.expires.toString()
//                    ))
//                }
//                val parameters = Bundle()
//                parameters.putString("fields", "id,name,email,gender,birthday")
//                request.parameters = parameters
//                request.executeAsync()
//            }
//
//            override fun onCancel() {
//                // App code
//            }
//
//            override fun onError(exception: FacebookException) {
//                exception.printStackTrace()
//                Toast.makeText(requireContext(),exception.message,Toast.LENGTH_SHORT).show()
//                Log.e("Facebook : ",exception.toString())
//            }
//        })
//
//        facebookLoginBtn.setOnClickListener{
//            facebookBtn.performClick()
//        }

        binding.twitterLoginBtn.setOnClickListener{
            hendleTwitterAccount()
        }

        deviceLoginButton.deviceRedirectUri = Uri.parse( "https://www.flicknplay.com/login")
    }


    lateinit var twitter: Twitter

    fun hendleTwitterAccount(){
        GlobalScope.launch(Dispatchers.Default) {
            val builder = ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(TwitterConstants.CONSUMER_KEY)
                .setOAuthConsumerSecret(TwitterConstants.CONSUMER_SECRET)
                .setIncludeEmailEnabled(true)
            val config = builder.build()
            val factory = TwitterFactory(config)
            twitter = factory.instance
            try {
                val requestToken = twitter.oAuthRequestToken
                withContext(Dispatchers.Main) {
                    setupTwitterWebviewDialog(requestToken.authorizationURL)
                }
            } catch (e: IllegalStateException) {
                Log.e("ERROR: ", e.toString())
            }
        }
    }


    lateinit var twitterDialog: Dialog
    var accToken: AccessToken? = null

    // Show twitter login page in a dialog
    @SuppressLint("SetJavaScriptEnabled")
    fun setupTwitterWebviewDialog(url: String) {
        twitterDialog = Dialog(requireContext())
        val webView = WebView(requireContext())
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.webViewClient = TwitterWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        twitterDialog.setContentView(webView)
        twitterDialog.show()
    }

    // A client to know about WebView navigations
    // For API 21 and above
    @Suppress("OverridingDeprecatedMember")
    inner class TwitterWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request?.url.toString().startsWith(TwitterConstants.CALLBACK_URL)) {
                Log.d("Authorization URL: ", request?.url.toString())
                handleUrl(request?.url.toString())

                if (request?.url.toString().contains(TwitterConstants.CALLBACK_URL)) {
                    twitterDialog.dismiss()
                }
                return true
            }
            return false
        }


        // For API 19 and below
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith(TwitterConstants.CALLBACK_URL)) {
                Log.d("Authorization URL: ", url)
                handleUrl(url)
                if (url.contains(TwitterConstants.CALLBACK_URL)) {
                    twitterDialog.dismiss()
                }
                return true
            }
            return false
        }


        private fun handleUrl(url: String) {
            val uri = Uri.parse(url)
            val oauthVerifier = uri.getQueryParameter("oauth_verifier") ?: ""
            GlobalScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        val request = SocialLoginModel(
                            twitter.verifyCredentials().id.toString(),
                            twitter.verifyCredentials().name,
                            twitter.verifyCredentials().email,
                            twitter.verifyCredentials().miniProfileImageURL,
                            twitter.verifyCredentials().url,
                            twitter.getOAuthAccessToken(oauthVerifier).token,
                            twitter.getOAuthAccessToken(oauthVerifier).tokenSecret,
                            ""
                        )
                        presenter.googleRegister("twitter",UserApi.REGISTER_SOCIAL_ACCOUNT,request)

                    }
            }

        }

    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun startSignUp() {
        val request = RegisterRequestModel(
            editUsername.text.toString(),
            editPassword.text.toString(),
            confirmationPassword.text.toString(),
            "flicknplayapi"
        )
        presenter.register(request)
    }

    private fun goToLoginFragment() {
        (activity as MainActivity).replaceFragment(LoginFragment.newInstance())
    }


    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun onRegistered(registerResponse: RegisterResponse) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, registerResponse.message, Toast.LENGTH_LONG).show()
        goToLoginFragment()
    }


    override fun onTwitterRegistered(registerResponse: RegisterResponse) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, registerResponse.message, Toast.LENGTH_LONG).show()



        (activity as MainActivity).replaceFragment(WhoIsWatchingFragment.newInstance())
    }

    override fun onShowProgress() {
        loadingProgressBar.visibility = View.VISIBLE
    }


    override fun onHideProgress() {
        loadingProgressBar.visibility = View.GONE
    }

    override fun onErrorWorksLoaded(errorResponse: ErrorResponse) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(
            appContext, errorResponse.errors.email[0], Toast.LENGTH_LONG
        ).show()
    }

    override fun onGoogleRegisterd(googleRegisterResponse: LoginResponse) {

        Constants.ACCESS_TOKEN = googleRegisterResponse.user.access_token
        Constants.USER_ID = googleRegisterResponse.user.id
        Constants.USER_ACCOUNT_ID = googleRegisterResponse.user.id

        val appContext = context?.applicationContext ?: return
        (activity as MainActivity).replaceFragment(WhoIsWatchingFragment.newInstance())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
        if(requestCode==MainActivity.RC_SIGN_IN){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(Exception::class.java)
                val credentials = GoogleAuthProvider.getCredential(account.idToken!!,null)


                auth.signInWithCredential(credentials)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if(task.isSuccessful){
                            Log.e("Email Login",auth.currentUser?.email.toString())


                            val request = SocialLoginModel(
                                account.id,
                                account.displayName,
                                account.email,
                                account.photoUrl.toString(),
                                "",
                                account.idToken,
                                "",
                                ""
                            )

                            presenter.googleRegister("google",UserApi.REGISTER_SOCIAL_ACCOUNT,request)

                            }



                    }
            }catch (ex: Exception){
                ex.printStackTrace()
            }

        }
    }


    private fun login( user_access_token:String , userId:Int){
        val welcome = getString(R.string.welcome)
        Constants.ACCESS_TOKEN = user_access_token
        Constants.USER_ID = userId
        Constants.USER_ACCOUNT_ID = userId
        SharedPreferencesHelper.writeAccessToken(requireActivity(), Constants.ACCESS_TOKEN)
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
        goToWhoIsWatchingFragment()
    }

    private fun goToWhoIsWatchingFragment() {
        (activity as MainActivity).replaceFragment(WhoIsWatchingFragment.newInstance())
    }
    var dialog:Dialog? = null
    private fun showDialog(codeString: String) {
        dialog = Dialog(requireContext())
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.layout_facebook_dialog)
        val code = dialog?.findViewById(R.id.tv_des) as TextView
        code.text = codeString
        val closeBtn = dialog?.findViewById(R.id.closeBtn) as Button

        closeBtn.setOnClickListener {
            dialog?.dismiss()
        }

        dialog?.show()

    }

    override fun genrateTokeResponse(firstCall: FacebookLoginSDK.FirstCall) {
        onHideProgress()
        showDialog(firstCall.user_code.toString())
    }

    override fun getUserAccessToken(secondCall: FacebookLoginSDK.SecondCall) {
        dialog?.dismiss()
        onShowProgress()
    }

    override fun getUserInformation(thirdCall: FacebookLoginSDK.ThirdCall,user_access_token:String) {
        presenter.googleRegister("facebook",
            UserApi.REGISTER_SOCIAL_ACCOUNT,
            SocialLoginModel(
                thirdCall.id,
                thirdCall.name,
                thirdCall.email,
                "",
                "" ,
                user_access_token,
                "",
                ""
            )
        )
    }

}
