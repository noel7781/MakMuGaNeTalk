import tokenReducer from "./Auth";
import { configureStore } from "@reduxjs/toolkit";

export default configureStore({
  reducer: {
    authToken: tokenReducer,
  },
});
