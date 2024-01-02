import createPage from "../components/createPage";


function submitForm(event) {
   
}

function createLoginForm () {

    
    const form = document.createElement("form");

    form.addEventListener("submit", (event) => {
        event.preventDefault();

        const username = userInput.value;
        const password = passwordInput.value;
    
        console.log(`Username ${username} \n Password: ${password}`)
        // get user info from api
        const user = {
            username,
            password
        }
        window.sessionStorage.setItem('userInfo', JSON.stringify(user))
        window.location.href = "/"
    })

    const grid = document.createElement("div");
    grid.classList.add("grid");
  
    const userLabel = document.createElement("label");
    userLabel.setAttribute("for", "username");
    userLabel.innerText = "UserName";
  
    const userInput = document.createElement("input");
    userInput.setAttribute("type", "text");
    userInput.setAttribute("id", "username");
    userInput.setAttribute("name", "username");
    userInput.setAttribute("placeholder", "Username");
    userInput.setAttribute("required", "true");
  
    const passwordLabel = document.createElement("label");
    passwordLabel.setAttribute("for", "password");
    passwordLabel.innerText = "Password";
  
    const passwordInput = document.createElement("input");
    passwordInput.setAttribute("type", "password");
    passwordInput.setAttribute("id", "password");
    passwordInput.setAttribute("name", "password");
    passwordInput.setAttribute("placeholder", "Password");
    passwordInput.setAttribute("required", "true");
  
    const buttonGroup = document.createElement("div");
    buttonGroup.classList.add("btnGroup");
  
    const canceltBtn = document.createElement("button");
    canceltBtn.innerText = `Cancel`;
    canceltBtn.classList.add("outline", "secondary")
    canceltBtn.addEventListener("click", () => {
        window.location.href = '/';
    })
    const submitBtn = document.createElement("input");
    submitBtn.setAttribute("type", "submit");
    //   submitBtn.innerText = `Submit`;
  
    buttonGroup.append(canceltBtn, submitBtn);
    form.append(userLabel, userInput, passwordLabel, passwordInput, buttonGroup);

    return form;
}


export default function login() {
  createPage("Login");
  const pageContent = document.querySelector("#pageContent");
  const section = document.createElement("section");
  section.classList.add("container");
  section.append(createLoginForm());
  pageContent.append(section);

  
}
