using FunTokenz.Filter;
using FunTokenz.Models.Data;
using FunTokenz.Models.View;
using FunTokenz.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using System.Threading.Tasks;

namespace FunTokenz.Controllers
{

    


    [Authorize]
    [ServiceFilter(typeof(MustBeSignedIn))]
    [ValidateSessionAttribute]
    public class AccountController : BaseController
    {
        private readonly IConfiguration _configuration;
        private readonly ILogger<AccountController> _logger;
        private readonly IIdentity _Identity;
        private readonly IRDS _RDS;
        
        private readonly UserManager<ApplicationUser> _userManager;
     

        public AccountController(IConfiguration configuration, ILogger<AccountController> logger, IIdentity Identity, IRDS RDS, UserManager<ApplicationUser> userManager) : base(logger, RDS, userManager)
        {
            _configuration = configuration;
            _logger = logger;
            _Identity = Identity;
            _RDS = RDS;
            _userManager = userManager;
        }

        public IActionResult Index()
        {
            return View();
        }



        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> AccountValidation([FromForm] MediaFileItemForm form)
        {
            _logger.LogInformation("Validatation Upload 1");
            if (ModelState.IsValid)
            {
                _logger.LogInformation("Validatation Upload 2");
                foreach (IFormFile item in form.files)
                {
                    var result = _RDS.SetMediaFile(item, "Account Validation", form.Category);
                }
            }

            FilesModel model = new FilesModel();
            model = _RDS.GetFiles();
            return PartialView("_FileView", model);

        }


    }
}
