import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import ErrorPage from "./error-page";
import Root from "./routes/root";
import Index from "./routes";
import Main from "./routes/main";
import { action as signInAction } from "./routes/signIn";
import { CookiesProvider } from "react-cookie";
import SignUp from "./routes/signup";
import Mychat from "./routes/mychat";
import Groupchat from "./routes/groupchat";
import ChatContainer from "./components/chatContainer";
import InviteAlarm from "./routes/invitealarm";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    errorElement: <ErrorPage />,
    children: [
      {
        errorElement: <ErrorPage />,
        children: [
          { index: true, element: <Index /> },
          {
            path: "signin",
            action: signInAction,
            errorElement: <div>Login Error!</div>,
          },
          {
            path: "main",
            element: <Main />,
          },
          {
            path: "signup",
            element: <SignUp />,
          },
          {
            path: "my-chat",
            element: <Mychat />,
          },
          {
            path: "group-chat",
            element: <Groupchat />,
          },
          {
            path: "chatRooms/:chatRoomId",
            element: <ChatContainer />,
          },
          {
            path: "invite",
            element: <InviteAlarm />,
          },
        ],
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <CookiesProvider>
      <RouterProvider router={router} />
    </CookiesProvider>
  </React.StrictMode>
);
