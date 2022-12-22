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
      return resp;
    })
    .catch((e) => console.error(e));
  return response;
};

export const getChatRoomList = async (
  tagList = [],
  keyword = "",
  pageNumber = 0,
  joined = false
) => {
  const response = await AxiosClient({
    method: "get",
    url: "/chat-rooms",
    params: {
      tagList: tagList.length > 0 ? tagList.toString() : tagList,
      keyword,
      joined,
      page: pageNumber,
      size: 10,
    },
  })
    .then((resp) => {
      return resp;
    })
    .catch((e) => console.error(e));
  return response;
};

export const clickLikeButton = async (id, like_state) => {
  const response = await AxiosClient({
    method: "post",
    url: "/chat-rooms-likes",
    data: { chatRoomId: id, likeState: like_state },
  })
    .then((resp) => {
      return resp;
    })
    .catch((e) => console.error(e));
  return response;
};

export const getMessages = async (id) => {
  const response = await AxiosClient({
    method: "get",
    url: "/chat-rooms/messages",
    params: { chatRoomId: id },
  })
    .then((resp) => {
      return resp;
    })
    .catch((e) => console.error(e));
  return response;
};
