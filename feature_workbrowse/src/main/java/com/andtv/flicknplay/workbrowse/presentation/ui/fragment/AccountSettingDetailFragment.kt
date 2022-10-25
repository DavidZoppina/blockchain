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
import androidx.leanback.app.BrowseSupportFragment
import com.andtv.flicknplay.data.Constants
import com.andtv.flicknplay.presentation.extension.replaceFragment
import com.andtv.flicknplay.workbrowse.R
import com.andtv.flicknplay.workbrowse.data.remote.api.UserApi
import com.andtv.flicknplay.workbrowse.data.remote.model.AllSocialResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.ErrorResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialLoginModel
import com.andtv.flicknplay.workbrowse.data.remote.model.SocialResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.AccountDetailRequest
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.ResetPasswordRequest
import com.andtv.flicknplay.workbrowse.data.remote.model.accountSettingDetail.User
import com.andtv.flicknplay.workbrowse.data.remote.model.login.LoginResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.register.RegisterRequestModel
import com.andtv.flicknplay.workbrowse.data.remote.model.register.RegisterResponse
import com.andtv.flicknplay.workbrowse.databinding.FragmentAccountSettingDetailBinding
import com.andtv.flicknplay.workbrowse.presentation.presenter.AccountSettingDetailPresenter
import com.andtv.flicknplay.workbrowse.presentation.ui.activity.MainActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
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

class AccountSettingDetailFragment : Fragment() , AccountSettingDetailPresenter.View , View.OnClickListener ,AdapterView.OnItemSelectedListener ,FacebookLoginSDK.FacebookLoginResponse{


    private val fragmentAdapter by lazy { BrowseSupportFragment.MainFragmentAdapter(this) }

    @Inject
    lateinit var presenter: AccountSettingDetailPresenter
    private lateinit var facebookLoginSDK:FacebookLoginSDK

    private var    user: User? = null

    //For Country Spinner
    private var countryCodes : MutableList<String> = ArrayList()
    private var countryNames : MutableList<String> = ArrayList()
    private var countrySelectedItem = 0

    //For Language Spinner
    private var languageCodes : MutableList<String> = ArrayList()
    private var languageNames : MutableList<String> = ArrayList()
    private var languageSelectedItem = 0


    //For TimeZone Spinner
    private var timeZoneCodes : MutableList<String> = ArrayList()
    private var timeZoneNames : MutableList<String> = ArrayList()
    private var timeZoneSelectedItem = 0

    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var callbackManager : CallbackManager
    private lateinit var binding:FragmentAccountSettingDetailBinding


    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivityComponent
            .accountSettingDetailFragmentComponent()
            .create(this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindTo(this.lifecycle)


        callbackManager = CallbackManager.Factory.create()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_setting_detail, container, false)
        return view
    }

    private fun initApiCalls(){
        Constants.ACCESS_TOKEN
        binding.loadingCl.visibility  = View.VISIBLE
        Constants.USER_ACCOUNT_ID?.let { presenter.getAccountSettingDetail(it) }


    }

    private fun connectGoogle(socialLoginModel: SocialLoginModel){
        presenter.socialConnection(UserApi.GOOGLE_PROVIDER,UserApi.CONNECT_SOCIAL_ACCOUNT,socialLoginModel)
    }
    private fun connectFacebook(socialLoginModel: SocialLoginModel){
        presenter.socialConnection(UserApi.FACEBOOK_PROVIDER,UserApi.CONNECT_SOCIAL_ACCOUNT,socialLoginModel)
    }
    private fun connectTwitter(socialLoginModel: SocialLoginModel){
        presenter.socialConnection(UserApi.TWITTER_PROVIDER,UserApi.CONNECT_SOCIAL_ACCOUNT,socialLoginModel)
    }

    private fun initUI(){
        countryNames.add("Select Country")
        languageNames.add("Select Language")
        timeZoneNames.add("Select timeZone")

        countryCodes.addAll(Locale.getISOCountries())
        for (countryCode in countryCodes) {
            val locale = Locale("", countryCode)
            countryNames.add( locale.displayCountry)
        }

        languageCodes.addAll(Locale.getISOLanguages())
        for (languageCode in languageCodes) {
            val locale = Locale(languageCode, "")
            languageNames.add(locale.displayLanguage)
        }

        timeZoneCodes.addAll(TimeZone.getAvailableIDs())
        for (timeZoneCode in timeZoneCodes) {
            timeZoneNames.add(timeZoneCode)
        }

        val countryArrayAdapter = activity?.let {
            ArrayAdapter(it,android.R.layout.simple_spinner_item,countryNames)
        }
        binding.countrySp.adapter = countryArrayAdapter
        binding.countrySp.onItemSelectedListener = this


        val languageArrayAdapter = activity?.let {
            ArrayAdapter(it,android.R.layout.simple_spinner_item,languageNames)
        }
        binding.languageSp.adapter = languageArrayAdapter
        binding.languageSp.onItemSelectedListener = this

        val timeZoneArrayAdapter = activity?.let {
            ArrayAdapter(it,android.R.layout.simple_spinner_item,timeZoneNames)
        }
        binding.timeZoneSp.adapter = timeZoneArrayAdapter
        binding.timeZoneSp.onItemSelectedListener = this

        binding.googleEnableBt.setOnClickListener(this)
        binding.facebookEnableBt.setOnClickListener(this)
        binding.twitterEnableBt.setOnClickListener(this)
        binding.updateNameBt.setOnClickListener(this)
        binding.updateProfileBt.setOnClickListener(this)
        binding.saveAccountPreferencesBt.setOnClickListener(this)


        /** Custom Facebook SDK */
        facebookLoginSDK = FacebookLoginSDK(requireActivity())
        facebookLoginSDK.facebookLoginResponse = this



//        facebook_btn.setPermissions(Arrays.asList("email"))
//        facebook_btn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//            override fun onSuccess(loginResult: LoginResult) {
//
//                val request = GraphRequest.newMeRequest(
//                    loginResult.accessToken
//                ) { _object, response ->
//
//                    connectFacebook(SocialLoginModel(
//
//                            _object?.getString("id"),
//                            _object?.getString("name"),
//                            _object?.getString("email"),
//                            "",
//                            "" ,
//                            loginResult.accessToken.token,
//                            "",
//                            loginResult.accessToken.expires.toString(),
//                        Constants.USER_ACCOUNT_ID
//                        ))
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

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountSettingDetailBinding.inflate(layoutInflater)
        initApiCalls()
        initUI()


    }

    override fun onPasswordUpdate(socialResponse: SocialResponse) {
        Toast.makeText(requireContext(),socialResponse.message,Toast.LENGTH_SHORT).show()
    }

    override fun onAllSocialAccount(allSocialResponse: AllSocialResponse) {
        if(allSocialResponse.status == 1){
            allSocialResponse.dataList?.let {
                it.forEach { item ->
                    if(item.service_name.equals("google",true)){
                        binding.googleEnableBt.text = getString(R.string.disable)
                    }else if(item.service_name.equals("facebook",true)){
                        binding.facebookEnableBt.text = getString(R.string.disable)
                    }else if(item.service_name.equals("twitter",true)){
                        binding.twitterEnableBt.text = getString(R.string.disable)
                    }
                }
            }
        }
    }

    override fun onAccountUpdate(socialResponse: SocialResponse) {
        Toast.makeText(requireContext(),socialResponse.message,Toast.LENGTH_SHORT).show()
    }

    override fun onSocialAccountResponse(socialResponse: AllSocialResponse) {
        Toast.makeText(requireContext(),socialResponse.message,Toast.LENGTH_SHORT).show()
        if(socialResponse.status == 1){
            socialResponse.dataList?.let {
                it.forEach { item ->
                    if(socialResponse.message.equals("connected",true)) {
                        if (item.service_name.equals("google", true)) {
                            binding.googleEnableBt.text = getString(R.string.disable)
                        } else if (item.service_name.equals("facebook", true)) {
                            binding.facebookEnableBt.text = getString(R.string.disable)
                        } else if (item.service_name.equals("twitter", true)) {
                            binding.twitterEnableBt.text = getString(R.string.disable)
                        }
                    }else{
                        if (item.service_name.equals("google", true)) {
                            binding.googleEnableBt.text = getString(R.string.enable)
                        } else if (item.service_name.equals("facebook", true)) {
                            binding.facebookEnableBt.text = getString(R.string.enable)
                        } else if (item.service_name.equals("twitter", true)) {
                            binding.twitterEnableBt.text = getString(R.string.enable)
                        }
                    }
                }
            }
        }
    }

    override fun onGetAccountSettingDetail(user: User) {
        this.user=user


        binding.firstNameEt.setText(user.first_name)
        binding.lastNameEt.setText(user.last_name)

        val userLanguage = languageCodes.indexOf(user.language.toString().trim())
        if(userLanguage > -1) binding.languageSp.setSelection(userLanguage +1 )


        val userCountry = countryCodes.indexOf((user.country.toString()).trim().uppercase())
        if(userCountry > -1) binding.countrySp.setSelection(userCountry +1 )

        val userTimeZone = timeZoneCodes.indexOf((user.timezone.toString()).trim())
        if(userTimeZone > -1) binding.timeZoneSp.setSelection(userTimeZone +1 )


        presenter.getAllSocialProfile(SocialLoginModel(email = user.email))

    }

    override fun onGetAccountSettingDetailFailed() {
        Toast.makeText(requireContext(),"Server Error", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            binding.saveAccountPreferencesBt.id->{
                var language:String? = null
                var country:String? = null
                var timezone:String? = null

                if(countrySelectedItem>0){
                    country = countryCodes.get(countrySelectedItem-1)
                }
                if(languageSelectedItem>0){
                    language = languageCodes.get(languageSelectedItem-1)
                }

                if(timeZoneSelectedItem>0){
                    timezone = timeZoneCodes.get(timeZoneSelectedItem-1)
                }

                presenter.updateUserProfile(Constants.USER_ACCOUNT_ID.toString(), AccountDetailRequest(
                    first_name = binding.firstNameEt.text.toString(),
                    last_name =binding.lastNameEt.text.toString(),
                    user?.avatar,
                    language,
                    country?.lowercase(),
                    timezone

                ))
            }
            binding.updateProfileBt.id ->{
                if(binding.newPasswordEt.text.toString().equals(binding.confirmPasswordEt.text.toString())) {
                        presenter.updatePassword(
                            ResetPasswordRequest(
                                email = user?.email,
                                current_password = binding.currentPasswordEt.text.toString(),
                                new_password = binding.newPasswordEt.text.toString(),
                                new_password_confirmation =binding.confirmPasswordEt.text.toString()
                            )
                        )
                    }
                else{
                    Toast.makeText(requireContext(),"Make show new password and confirm password are same.",Toast.LENGTH_SHORT).show()
                }
            }
            binding.googleEnableBt.id ->{
                if(binding.googleEnableBt.text.toString().equals(getString(R.string.enable),true)){
                    callbackManager = CallbackManager.Factory.create()
                    val gso =  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("1018650312961-esergp9t1cpe02iuruqqbqk1sqrp5h4f.apps.googleusercontent.com")
                        .requestEmail()
                        .build()
                    googleSignInClient = GoogleSignIn.getClient(requireContext(),gso)
                    auth = Firebase.auth

                    val intent = googleSignInClient.signInIntent
                    startActivityForResult(intent, MainActivity.RC_SIGN_IN)
                }else{
                    presenter.socialConnection(UserApi.GOOGLE_PROVIDER,UserApi.DISCONNECT_SOCIAL_ACCOUNT,
                        SocialLoginModel(
                        email = user?.email
                    )
                    )
                }
            }
            binding.facebookEnableBt.id->{
                if(binding.googleEnableBt.text.toString().equals(getString(R.string.enable),true)) {
                    onShowProgress()
                    facebookLoginSDK.genrateToken()
                }else{
                    presenter.socialConnection(UserApi.FACEBOOK_PROVIDER,UserApi.DISCONNECT_SOCIAL_ACCOUNT,SocialLoginModel(
                        email = user?.email
                    ))
                }
            }
            binding.twitterEnableBt.id->{
                if(binding.googleEnableBt.text.toString().equals(getString(R.string.enable),true)) {
                    hendleTwitterAccount()
                }else{
                    presenter.socialConnection(UserApi.TWITTER_PROVIDER,UserApi.DISCONNECT_SOCIAL_ACCOUNT,SocialLoginModel(
                        email = user?.email
                    ))
                }
            }
            binding.updateNameBt.id ->{
                updateName()
            }
        }
    }

    fun updateName(){
        presenter.updateUserProfile(Constants.USER_ACCOUNT_ID.toString(), AccountDetailRequest(
            first_name = binding.firstNameEt.text.toString(),
            last_name =binding.lastNameEt.text.toString(),
            user?.avatar,
            user?.language,
            user?.country,
            user?.timezone

        ))
    }

    companion object {
        fun newInstance() =
            AccountSettingDetailFragment()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0?.id){
            binding.countrySp.id -> {
                countrySelectedItem = p2
                return
            }
            binding.languageSp.id -> {
                languageSelectedItem = p2
                return
            }
            binding.timeZoneSp.id ->{
                timeZoneSelectedItem = p2
                return
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

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
                        "",
                        Constants.USER_ACCOUNT_ID
                    )
                   connectTwitter(request)

                }
            }

        }

    }

    override fun onShowProgress() {
        binding.loadingCl.visibility = View.VISIBLE
    }


    override fun onHideProgress() {
        binding.loadingCl.visibility = View.GONE
    }

    override fun onErrorWorksLoaded(errorResponse: ErrorResponse) {


    }
    override fun onError(message: String) {
    Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()

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
                                "",
                                Constants.USER_ACCOUNT_ID
                            )
                            connectGoogle(request)

                        }



                    }
            }catch (ex: Exception){
                ex.printStackTrace()
            }

        }
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
        connectFacebook(SocialLoginModel(
            thirdCall.id,
            thirdCall.name,
            thirdCall.email,
            "",
            "" ,
            user_access_token,
            "",
            "",
            Constants.USER_ACCOUNT_ID
        ))

    }

}