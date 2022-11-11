import AxiosClient from "./AxiosClient";

export const createChatRoom = async (title, tagList, isPublic, password) => {
  const userId = 1;
  const chatRoomType = isPublic ? "OPEN_CHAT" : "PRIVATE_CHAT";
  password = isPublic ? "" : password;
  const response = await AxiosClient({
    method: "post",
    url: "/chat-rooms",
    data: { userId, title, tagList, chatRoomType, password },
  })
    .then((resp) => {
      console.log(resp);
      return resp;
    })
    .catch((e) => console.error(e));
  return response;
};
