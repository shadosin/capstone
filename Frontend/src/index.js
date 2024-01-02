// import manifest from './manifest.json';
// import serviceWorker from './service-worker';

import logo from "./assets/vitatrac.png";
import hero from "./assets/hero.png";
import pico from "./css/pico.css";
import styles from "./css/style.css";
import Navigo from "navigo";

import navbar from "./navbar";

import home from "./pages/home";
import login from "./pages/login";
import dashboard from "./pages/dashboard";

document.addEventListener("DOMContentLoaded", () => {
  console.log(`Document Loaded`);
  console.log(`Loading NavBar`);
  navbar();

  const userInfo = JSON.parse(window.sessionStorage.getItem("userInfo"));

  if (userInfo) {
    // load dashboard page
    dashboard();
  } else {
    home();
  }
});

if (window.sessionStorage.getItem("userInfo")) {
  console.log(JSON.parse(window.sessionStorage.getItem("userInfo")));
}

// const loginBtn = document.querySelector('#login')
// loginBtn.addEventListener("click", () => router.navigate("login"))
