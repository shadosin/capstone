import BaseClass from "../util/baseClass";
import axios from "axios";

import * as eventData from "./eventdata.js";

export default class ApiClient extends BaseClass {
  static baseUrl = "http://127.0.0.1:5001";

  constructor(props = {}) {
    super();
    const methodsToBind = [
      "clientLoaded",
      "createUser",
      "handleError",
      "createPayload",
      "loginUser",
      "getAllSchedules",
      "getOneSchedule",
      "getCurrentSchedule",
      "createSchedule",
      "editSchedule",
      "deleteSchedule",
    ];
    try {
      this.bindClassMethods(methodsToBind, this);
    } catch (error) {
      console.warn(error);
    }
    this.props = props;

    this.clientLoaded(axios);
    document.addEventListener("DOMContentLoaded", () => {
      window.localStorage.setItem(
        "mealBreakfast",
        JSON.stringify(eventData.mealBreakfast),
      );
      window.localStorage.setItem(
        "mealLunch",
        JSON.stringify(eventData.mealLunch),
      );
      window.localStorage.setItem(
        "mealDinner",
        JSON.stringify(eventData.mealDinner),
      );
      window.localStorage.setItem(
        "mealSnack",
        JSON.stringify(eventData.mealSnack),
      );
      window.localStorage.setItem(
        "exerciseCardio",
        JSON.stringify(eventData.exerciseCardio),
      );
      window.localStorage.setItem(
        "exerciseStrength",
        JSON.stringify(eventData.exerciseStrength),
      );
      window.localStorage.setItem(
        "exerciseFlexibility",
        JSON.stringify(eventData.exerciseFlexibility),
      );
      window.localStorage.setItem(
        "mealEvents",
        JSON.stringify(eventData.mealData),
      );
      window.localStorage.setItem(
        "exerciseEvents",
        JSON.stringify(eventData.exerciseData),
      );
    });
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
    const url = `${ApiClient.baseUrl}/user/login`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      return await this.client.post(url, payload, config);
    } catch (error) {
      this.handleError("loginUser", error, errorCallback);
    }
  }

  async createUser(data, errorCallback) {
    const payload = data;
    const url = `${ApiClient.baseUrl}/user/create`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      let response = await this.client.post(url, payload, config);
      return response.data;
    } catch (error) {
      this.handleError("createUser", error, errorCallback);
    }
  }

  async updateUserMetrics(payload, errorCallback) {
    const url = `${ApiClient.baseUrl}/healthMetrics/update`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      await this.client.post(url, payload, config).then((res) => {
        window.localStorage.setItem("metrics", JSON.stringify(res.data));
      });
    } catch (error) {
      this.handleError("updateUserMetrics", error, errorCallback);
    }
  }

  async editUser(data, errorCallback) {
    const payload = data;

    const { userId, weight, weightUnit } = payload;
    const metricUpdatePayload = {
      userId,
      weight,
      weightUnit: weightUnit.toUpperCase(),
    };

    const url = `${ApiClient.baseUrl}/user/${payload.userId}`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      await this.client
        .put(url, payload, config)
        .then(async (res) => {
          window.sessionStorage.setItem("userInfo", JSON.stringify(res.data));
        })
        .then(async () => {
          await this.updateUserMetrics(metricUpdatePayload);
        });
    } catch (error) {
      this.handleError("editUser", error, errorCallback);
    }
  }

  // Newly Added Schedule API methods
  async getAllSchedules(errorCallback) {
    const url = `${ApiClient.baseUrl}/user-schedule/`;
    try {
      return await this.client.get(url);
    } catch (error) {
      this.handleError("getAllSchedules", error, errorCallback);
    }
  }

  async getOneSchedule(scheduleId, errorCallback) {
    const url = `${ApiClient.baseUrl}/user-schedules/${scheduleId}`;
    try {
      return await this.client.get(url);
    } catch (error) {
      this.handleError("getOneSchedule", error, errorCallback);
    }
  }

  async getCurrentSchedule(userId, errorCallback) {
    const url = `${ApiClient.baseUrl}/user-schedules/current/${userId}`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      return await this.client.get(url, config);
    } catch (error) {
      this.handleError("createSchedule", error, errorCallback);
    }
  }

  async editSchedule(scheduleId, data, errorCallback) {
    const payload = data;
    const url = `${ApiClient.baseUrl}/user-schedule/${scheduleId}`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      return await this.client.put(url, payload, config);
    } catch (error) {
      this.handleError("editSchedule", error, errorCallback);
    }
  }

  async deleteSchedule(scheduleId, errorCallback) {
    const url = `${ApiClient.baseUrl}/user-schedule/${scheduleId}`;
    try {
      return await this.client.delete(url);
    } catch (error) {
      this.handleError("deleteSchedule", error, errorCallback);
    }
  }

  async createSchedule(userId, currentWeek, errorCallback) {
    const url = `${ApiClient.baseUrl}/user-schedules/`;
    const payload = { userId, ...currentWeek };
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };
    try {
      return await this.client.post(url, payload, config);
    } catch (error) {
      this.handleError("createSchedule", error);
    }
  }

  async updateUserInfo(userId, errorCallback) {
    const url = `${ApiClient.baseUrl}/user/${userId}/`;
    try {
      await this.client
        .get(url)
        .then((res) => {
          window.sessionStorage.setItem("userInfo", JSON.stringify(res.data));
        })
        .then((res) => {
          return res;
        });
    } catch (error) {
      this.handleError("updateUserInfo", error, errorCallback);
    }
  }

  async getUserMetrics(userId) {
    const url = `${ApiClient.baseUrl}/healthMetrics/${userId}`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    try {
      const response = await this.client.get(url, config);
      console.log(response);
      return response.data; // Return the data directly
    } catch (error) {
      console.error("Error in getUserMetrics:", error);
      return null; // Return null or a custom error object
    }
  }

  async getMealEvent(mealId) {
    const url = `${ApiClient.baseUrl}/eventdata/meal/${mealId}`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    const data = JSON.parse(window.localStorage.getItem("mealEvents"));

    if (data) {
      for (let meal of data) {
        if (meal.mealId === mealId) {
          return meal;
        }
      }
    }

    if (!data) {
      try {
        const response = await this.client.get(url, config);
        console.log(response);
        return response.data; // Return the data directly
      } catch (error) {
        console.error("Error in getMealEvent:", error);
        return null; // Return null or a custom error object
      }
    }
  }

  async getExerciseEvent(exerciseId) {
    const url = `${ApiClient.baseUrl}/eventdata/exercise/${exerciseId}`;
    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    const data = JSON.parse(window.localStorage.getItem("exerciseEvents"));
  console.log(data);

    if (data) {
      for (let exercise of data) {
        console.log(exercise);
        console.log(exercise.exerciseId, exerciseId)
        if (exercise.exerciseId === exerciseId) {
          return exercise;
        }
      }
    }

    if (!data) {
      try {
        const response = await this.client.get(url, config);
        console.log(response);
        return response.data; // Return the data directly
      } catch (error) {
        console.error("Error in getMealEvent:", error);
        return null; // Return null or a custom error object
      }
    }
  }

  async getAllMealEvents(type) {
    const url = `${ApiClient.baseUrl}/eventdata/meal/type/${type}`;

    const data = JSON.parse(window.localStorage.getItem(`meal${type}`));

    if (!data) {
      try {
        await this.client.get(url).then((res) => {
          window.localStorage.setItem(`meal${type}`, JSON.stringify(res.data));
        });
      } catch (error) {
        console.error(" Network Error in getAllMealEvents:", error);
        return error; // Return null or a custom error object
      }
    }

    return data || [];
  }

  async getAllExerciseEvents(type) {
    const url = `${ApiClient.baseUrl}/eventdata/exercise/type/${type}`;

    const data = JSON.parse(window.localStorage.getItem(`exercise${type}`));

    if (!data) {
      try {
        await this.client.get(url).then((res) => {
          window.localStorage.setItem(
            `exercise${type}`,
            JSON.stringify(res.data),
          );
        });
      } catch (error) {
        console.error(" Network Error in getAllexerciseEvents:", error);
        return error; // Return null or a custom error object
      }
    }

    return data || [];
  }

  handleError(method, error, errorCallback) {
    console.error(method + " failed - " + error);
    if (error.response?.data && error.response.data.message !== undefined) {
      console.error(error.response.data.message);
    }
    if (errorCallback) {
      errorCallback(method + " failed - " + error);
    }
  }
}
