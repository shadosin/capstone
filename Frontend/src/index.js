
import logo from "./assets/vitatrac.png";
import vegetables from "./assets/vegetables.jpg";
import hero from "./assets/hero.png";
import pico from "./css/pico.css";
import styles from "./css/style.css";

import navbar from "./navbar";

import home from "./pages/home";
import dashboard from "./pages/dashboard";

window.onstorage = (event) => {
  ;
};

document.addEventListener("DOMContentLoaded", () => {
  ;
  ;
  navbar();

  const userInfo =
    JSON.parse(window.sessionStorage.getItem("userInfo")) ||
    JSON.parse(window.localStorage.getItem("userInfo"));

  if (userInfo) {
    // load dashboard page
    dashboard();
  } else {
    home();
  }
});
