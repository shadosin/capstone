import ApiClient from "../api/apiClient";
import createPage from "../components/createPage";
import formFields from "./formFields.json";

export default function editUser() {
  const client = new ApiClient();
  const app = document.querySelector("#app");

  createPage("Edit User Profile");

  const editUserForm = document.createElement("form");
  editUserForm.id = "editUserForm";

  const fields = document.createElement("div");
  fields.classList.add("formArea");

  function createInputField(id, label, type, placeholder, isRequired, value) {
    const field = document.createElement("div");
    let inputElement;

    if (id === "weightUnit") {
      const labelElement = document.createElement("label");
      labelElement.htmlFor = id;
      labelElement.textContent = label;

      inputElement = document.createElement("select");
      inputElement.id = id;
      inputElement.name = id;
      inputElement.required = true;
      inputElement.innerHTML = `
        <option value="lbs">lbs</option>
        <option value="kg">kg</option>
        `;
      field.classList.add("formInput");
      field.append(labelElement, inputElement);
    } else if (id === "username" || id === "userId") {
      const labelElement = document.createElement("label");
      labelElement.htmlFor = id;
      labelElement.textContent = `${label}: ${value}`;
      field.classList.add("formInput");
      field.append(labelElement);
    } else {
      const labelElement = document.createElement("label");
      labelElement.htmlFor = id;
      labelElement.textContent = label;
      inputElement = document.createElement("input");
      inputElement.type = type;
      inputElement.id = id;
      inputElement.name = id;
      if (value) {
        inputElement.value = value;
      }
      inputElement.placeholder = placeholder;
      if (isRequired) inputElement.setAttribute("required", "true");

      if (id === "email") {
        field.classList.add("emailAddressInput");
      } else {
        field.classList.add("formInput");
      }
      field.append(labelElement, inputElement);
    }
    fields.append(field);
  }

  const userInfo = JSON.parse(window.sessionStorage.getItem("userInfo"));

  formFields.forEach((field) => {
    const value = userInfo[field.id];
    const { id, label, type, placeholder, required } = field;
    createInputField(id, label, type, placeholder, required, value);
  });

  const { weight, weightUnit } = JSON.parse(
    window.localStorage.getItem("metrics"),
  );
  createInputField(
    "weight",
    "Weight",
    "number",
    "Weight",
    false,
    Number(weight),
  );
  createInputField(
    "weightUnit",
    "Weight Unit",
    "select",
    "Weight Unit",
    false,
    weightUnit,
  );

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

  buttonGroup.append(canceltBtn, submitBtn);

  editUserForm.append(fields, buttonGroup);
  app.append(editUserForm);
  editUserForm.addEventListener("submit", submitForm);

  async function submitForm(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    data.userId = userInfo.userId;

    for (let key in data) {
      if (key === "username") {
        delete data[key];
      }
      if (data[key] === "" || data[key] === undefined) {
        delete data[key];
      }
    }

    try {
      await client.editUser(data).then((response) => {
        window.location.href = "/";
      });
    } catch (error) {
      console.error(error);
    }
  }

  const pageContent = document.querySelector("#pageContent");
  pageContent.append(editUserForm);
}
