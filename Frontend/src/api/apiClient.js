import BaseClass from "../util/baseClass";
import axios from "axios";

export default class ApiClient extends BaseClass {
  //how do i create a constant variable for this class

  static baseUrl = "http://127.0.0.1:5001";

  constructor(props = {}) {
    super();
    const methodsToBind = [
      "clientLoaded",
      "createUser",
      "handleError",
      "createPayload",
      "loginUser",
    ];
    this.props = props;
    this.clientLoaded(axios);
  }

  clientLoaded(client) {
    this.client = client;
    if (this.props.hasOwnProperty("onReady")) {
      this.props.onReady();
    }
  }

  createPayload(formData) {
    return Object.fromEntries(formData.entries());
  }

  async loginUser(data, errorCallback) {
    const payload = data;
    const url = `http://localhost:5001/user/login`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      const response = await this.client.post(url, payload, config);
      return response.data;
    } catch (error) {
      this.handleError("loginUser", error, errorCallback);
    }
  }

  async createUser(data, errorCallback) {
    const payload = data;
    const url = `http://localhost:5001/user/create`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      console.log({ url, payload, config });
      let response = await this.client.post(url, payload, config);
      return response.data;
    } catch (error) {
      this.handleError("createUser", error, errorCallback);
    }
  }

  handleError(method, error, errorCallback) {
    console.error(method + " failed - " + error);
    if (error.response.data.message !== undefined) {
      console.error(error.response.data.message);
    }
    if (errorCallback) {
      errorCallback(method + " failed - " + error);
    }
  }
}
