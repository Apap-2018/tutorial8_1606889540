package com.apap.tutorial8.controller;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;
import com.apap.tutorial8.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userService;

    @Autowired
    private UserRoleDb userRoleDb;

    //untuk method add user
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    private String addUserSubmit(HttpServletRequest request, @ModelAttribute UserRoleModel user, Model model){
        String password = request.getParameter("password");

        /*
         * password harus divalidasi
         * kalau berhasil akan ada pesan sukses di html
         * kalau berhasil akan ada pesan error di html
         */
        if (userService.passValidator(password)){
            userService.addUser(user);
        }
        model.addAttribute("Msg", userService.passValidator(password));
        return "home";
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    private String updatePassword(HttpServletRequest request, Model model){

        /*
         * mengambil variabel masing - masing input
         * username yang login dicari lalu UserRoleModelnya dicari dari username itu
         * kalau UserRoleModelnya ada, nanti dibandingin oldpassword dr input
         * apakah sama dengan oldpassword yang ada di DB
         * setelah itu dicek apakah newPassword sama dengan newPasswordConfirm
         * kalau semua lolos, password akan diganti
         */
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String newPasswordConfirm = request.getParameter("newPasswordConf");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserRoleModel userCurrent = userRoleDb.findByUsername(auth.getName());

        /*
        state 1 = berhasil
        state 2 = kalau newPassword != newwPasswordConfirm
        state 3 = kalau newPassword panjangnya kurang dari 8
        state 4 = kalau oldPassword tidak cocok
        state 5 = kalau user tidak ada dalam DB
         */
        if(userCurrent!=null){
            if (userService.comparePass(oldPassword, userCurrent.getPassword())){
                if(userService.passValidator(newPassword)){
                    if (newPassword.equals(newPasswordConfirm)){
                        userService.updatePassword(userCurrent, newPassword);
                        model.addAttribute("state", 1);
                    }
                    else {
                        model.addAttribute("state", 2);
                    }
                }
                else {
                    model.addAttribute("state", 3);
                }
            }
            else {
                model.addAttribute("state", 4);
            }
        }
        else {
            model.addAttribute("state", 5);
        }
     return "home";
    }
}
