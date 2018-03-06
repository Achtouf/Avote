package fr.lyon1.avote.controller;

import fr.lyon1.avote.filters.Info;
import fr.lyon1.avote.filters.Md5;
import fr.lyon1.avote.logic.model.entity.User;
import fr.lyon1.avote.persistence.service.PollService;
import fr.lyon1.avote.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private List<Info> infoCntr;
    @Autowired
    private UserService userService;
    @Autowired
    private PollService pollService;

    @PostConstruct
    public void init() {
        infoCntr = new ArrayList<Info>();
    }

    @RequestMapping(value = {"", "/", "/signin", "/Signin"})
    public String showPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {
            return "redirect:/Dashboard";
        }

        User user = new User();
        model.addAttribute("user", user);
        return "signin";
    }

    @RequestMapping(value = {"/login", "/Login"})
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");
        User user = new User();

        if (checkSession(session) || userSession != null) {
            session.setAttribute("result", pollService.getAllPollWithoutRestrictions());
            return "redirect:/Dashboard";
        }

        model.addAttribute("user", user);
        return "login";
    }

    @RequestMapping("/SigninForm")
    public String signinForm(HttpServletRequest request, @ModelAttribute("user") User user) {
        HttpSession session = request.getSession();
        cleanMessageInfo(session);

        if (userService.getUserByEmail(user.getEmail()) == null) {
            Md5 Password = new Md5(user.getPassword());

            user.setPassword(Password.getWord());
            user.setActivated(true);
            userService.addUser(user);
        } else {
            addMessageInfo(session, "", "Votre compte existe déja, Veuillez vous connecter.", "warning");
        }

        return "redirect:/Login";
    }

    @RequestMapping("/LoginForm")
    public String loginForm(HttpServletRequest request, @ModelAttribute("user") User user) {
        User u = userService.getUserByEmail(user.getEmail());
        HttpSession session = request.getSession();
        cleanMessageInfo(session);

        if (u != null) {
            if (u.isActivated()) {
                if (!u.isDeleted()) {
                    String passEncoded = (String) new Md5(user.getPassword()).getWord();

                    if (u.getPassword().equals(passEncoded)) {
                        addMessageInfo(session, "Connexion", "Connexion réussie !", "success");
                        session.setAttribute("user", u);
                        return "redirect:/Dashboard";
                    } else {
                        addMessageInfo(session, "", "Mot de passe incorrecte !", "error");
                        return "redirect:/Login";
                    }
                } else {
                    addMessageInfo(session, "", "Ce compte n'existe plus !", "error");
                    return "redirect:/Signin";
                }
            } else {
                addMessageInfo(session, "", "Votre compte existe mais n'a pas ecnore été activé !", "info");
            }
        }

        // ERROR
        addMessageInfo(session, "", "Votre compte n'existe pas, veuillez vous inscrire ! <a href='Signin'>S'inscrire</a>", "error");
        return "redirect:/Login";
    }

    @RequestMapping(value = {"/dashboard", "/Dashboard"})
    public String dashboard(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            return "redirect:/Login";
        }

        session.setAttribute("result", pollService.getAllPollWithoutRestrictions());

        return "dashboard";
    }

    @RequestMapping(value = {"/logout", "/Logout"})
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        cleanMessageInfo(session);
        request.getSession().invalidate();

        return "redirect:/Login";
    }

    private Boolean checkSession(HttpSession session) {
        return session != null && session.getAttributeNames().hasMoreElements() && session.getAttribute("user") != null;
    }

    private void addMessageInfo(HttpSession session, String titleMessage, String contentMessage, String typeMessage) {
        infoCntr.clear();
        infoCntr.add(new Info(titleMessage, contentMessage, typeMessage));
        session.setAttribute("infos", infoCntr);
    }

    private void cleanMessageInfo(HttpSession session) {
        infoCntr.clear();
        session.setAttribute("infos", infoCntr);
    }

    // TODO
    // Separer les notifs de ce controller
}