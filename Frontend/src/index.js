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

const router = new Navigo("/");

// router.navigate("/login");
router.updatePageLinks();

window.addEventListener("load", () => {
  const render = (content) => {
    console.log(content);
    document.querySelector("main section").innerHTML = content;
  };
  router
    .on("/", function () {
      const content = home();
      console.log(content);
      render(content);
    })
    .on("/login", function () {
      const content = login();
      render(content);
    });

  router.notFound(function () {
    const content = "<h1>404 Error</h1>";
    render(content);
  });

  router.hooks({
    before: (done, params) => {
      console.log(params);
      done();
    },
    after: (params) => {
      console.log(params);
    },
  });
});

document.addEventListener("DOMContentLoaded", () => {
  console.log(`Document Loaded`);
  console.log(`Loading NavBar`);
  navbar();
});

// const loginBtn = document.querySelector('#login')
// loginBtn.addEventListener("click", () => router.navigate("login"))
