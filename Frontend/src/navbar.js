import home from "./pages/home";
import login from "./pages/login";
import registerPage from "./pages/register";


export default function navbar() {
// Create the nav element
const nav = document.createElement('nav');
nav.className = 'top-nav nav';

// Create the first ul
const ul1 = document.createElement('ul');

// Create the img element
const img = document.createElement('img');
img.className = 'logo';
img.src = 'vitatrac.png';
img.alt = 'logo';

// Create the first li element with strong
const li1 = document.createElement('li');
const strong = document.createElement('strong');
strong.textContent = 'VitaTrac';
li1.append(strong);

// Append img and li to the first ul
ul1.append(img, li1);

// Create the second ul
const ul2 = document.createElement('ul');
ul2.className = 'navBtnGroup';

// Create the first li in the second ul with an a element
const li2 = document.createElement('li');
li2.setAttribute('role', 'button');
li2.className = 'outline primary';
li2.textContent = 'Register';

// Create the second li in the second ul with an a element
const li3 = document.createElement('li');
li3.id = 'login';
const a2 = document.createElement('a');
li3.setAttribute('role', 'button');
li3.setAttribute('data-navigo', '');
li3.textContent = 'Login';
li3.append(a2);

// Append li elements to the second ul
ul2.append(li2, li3);

// Append both uls to the nav
nav.append(ul1, ul2);

// Select the header element and append the nav to it
const header = document.querySelector('header');
header.append(nav);

li2.addEventListener('click', () => {
    console.log("Register Clicked");
    document.querySelector("section").innerHTML = "";
    registerPage();
})

li3.addEventListener('click', () => {
    console.log("login clicked")
    const section = document.querySelector("section")
    section.innerHTML = "";

    login()
})

ul1.addEventListener('click', () => {
    console.log("home clicked");
    const section = document.querySelector("section")
    section.innerHTML = "";
    home();
})
}