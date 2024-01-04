import ApiClient from "../api/apiClient";
import createPage from "../components/createPage";

export default function login() {
  const client = new ApiClient();

  async function submitForm(event) {
    event.preventDefault();

    let formData = new FormData(event.target);

    try {
      const res = await client
        .loginUser(client.createPayload(formData))
        .then((res) => {
          console.log(`👍`);
          window.sessionStorage.setItem("userInfo", JSON.stringify(res));
          window.localStorage.setItem("userInfo", JSON.stringify(res));
        });
      if (res) {
        // window.location.href = "/";
      }
    } catch (error) {
      console.error(error);
    }
  }

  function createLoginForm() {
    const form = document.createElement("form");
    form.setAttribute("id", "loginForm");
    form.addEventListener("submit", submitForm);

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
    canceltBtn.classList.add("outline", "secondary");
    canceltBtn.addEventListener("click", () => {
      window.location.href = "/";
    });
    const submitBtn = document.createElement("input");
    submitBtn.setAttribute("type", "submit");
    //   submitBtn.innerText = `Submit`;

    buttonGroup.append(canceltBtn, submitBtn);
    const fields = document.createElement("div");
    fields.append(userLabel, userInput, passwordLabel, passwordInput);
    form.append(fields, buttonGroup);
    return form;
  }

  createPage("Login");
  const pageContent = document.querySelector("#pageContent");
  const section = document.createElement("section");
  section.classList.add("container");
  section.append(createLoginForm());
  pageContent.append(section);
}
