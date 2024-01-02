import home from "./pages/home";
import login from "./pages/login";
import registerPage from "./pages/register";

function createUserButtons() {
  console.log(`creating user buttons`);
  if (!window.sessionStorage.getItem("userInfo")) {
    const ul2 = document.createElement("ul");
    ul2.className = "navBtnGroup";

    // Create the first li in the second ul with an a element
    const li2 = document.createElement("li");
    li2.setAttribute("role", "button");
    li2.className = "outline";
    li2.textContent = "Register";

    // Create the second li in the second ul with an a element
    const li3 = document.createElement("li");
    li3.id = "login";
    li3.setAttribute("role", "button");
    li3.textContent = "Login";

    // Append li elements to the second ul
    ul2.append(li2, li3);
    li2.addEventListener("click", () => {
      console.log("Register Clicked");
      document.querySelector("section").innerHTML = "";
      registerPage();
    });

    li3.addEventListener("click", () => {
      console.log("login clicked");
      const section = document.querySelector("section");
      section.innerHTML = "";
      login();
    });
    return ul2;
  } else {
    const userBtnGroup = document.createElement("ul");
    userBtnGroup.className = "navBtnGroup";

    const logoutBtn = document.createElement("li");
    logoutBtn.setAttribute("role", "button");
    logoutBtn.className = "outline primary";
    logoutBtn.textContent = "Logout";

    logoutBtn.addEventListener("click", () => {
      window.sessionStorage.removeItem("userInfo");
      window.location.href = "/";
    });
    userBtnGroup.append(logoutBtn);

    return userBtnGroup;
  }
}

export default function navbar() {
  // Create the nav element
  const nav = document.createElement("nav");
  nav.className = "top-nav nav";

  // Create the first ul
  const ul1 = document.createElement("ul");

  // Create the img element
  const img = document.createElement("img");
  img.className = "logo";
  img.src = "vitatrac.png";
  img.alt = "logo";

  // Create the first li element with strong
  const li1 = document.createElement("li");
  const strong = document.createElement("strong");
  strong.textContent = "VitaTrac";
  li1.append(strong);

  // Append img and li to the first ul
  ul1.append(img, li1);

  const userInfo = JSON.parse(window.sessionStorage.getItem("userInfo")) || null;
  const userButtons = createUserButtons();

  if (userInfo) {
    const {username} = userInfo;
    const ul = document.createElement("ul");
    const li = document.createElement("li");
    const text = document.createElement("h5");
    text.innerText = `${username}'s Dashboard`;
    li.append(text);
    ul.append(li);

    nav.append(ul1, ul, userButtons);
  } else {
    nav.append(ul1, userButtons);
  }

  const header = document.querySelector("header");
  header.append(nav);

  ul1.addEventListener("click", () => {
    console.log("home clicked");
    const section = document.querySelector("section");
    section.innerHTML = "";
    window.location.href = '/'
  });
}
