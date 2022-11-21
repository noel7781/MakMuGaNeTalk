import AxiosClient from "./AxiosClient";

export const createChatRoom = async (title, tagList, isPublic, password) => {
  const chatRoomType = isPublic ? "OPEN_CHAT" : "PRIVATE_CHAT";
  password = isPublic ? "" : password;
  const response = await AxiosClient({
    method: "post",
    url: "/chat-rooms",
    data: { title, tagList, chatRoomType, password },
  })
    .then((resp) => {
      console.log(resp);
      return resp;
    })
    .catch((e) => console.error(e));
  return response;
};

export const inviteChatRoom = async (userNickname, inviteMessage) => {
  const inviteResponse = await AxiosClient({
    method: "get",
    url: "/users/nickname-check",
    params: { nickname: userNickname },
  });
  const inviteUserId = inviteResponse.data;
  if (inviteUserId == 0) {
    return 0;
  }
  const response = await AxiosClient({
    method: "post",
    url: "/chat-room-invitations",
    data: {
      guestUserId: inviteUserId,
      firstMessage: inviteMessage,
    },
  })
    .then((resp) => {
      console.log(resp);
      return resp;
    })
    .catch((e) => console.error(e));
  return response;
};

export const getChatRoomList = async (
  tagList = [],
  keyword = "",
  pageNumber = 0
) => {
  const response = await AxiosClient({
    method: "get",
    url: "/chat-rooms",
    params: { tagList, keyword },
  })
    .then((resp) => {
      console.log(resp);
      return resp;
    })
    .catch((e) => console.error(e));
  return response;
};
