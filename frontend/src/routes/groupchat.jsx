import LockIcon from "@mui/icons-material/Lock";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import NavigateBeforeIcon from "@mui/icons-material/NavigateBefore";
import NavigateNextIcon from "@mui/icons-material/NavigateNext";
import Loading from "../components/ui/loading";
import { useEffect, useState } from "react";
import { clickLikeButton, getChatRoomList } from "../apis/ChatRoomAPI";
import { useNavigate } from "react-router-dom";
import { getArrays } from "../utils/util";
import "../css/chatRoom.css";
import SearchBar from "../components/ui/searchbar";
import Card from "../components/ui/card";
const Groupchat = () => {
  const [chatRoomList, setChatRoomList] = useState([]);
  const [totalPage, setTotalPage] = useState(0);
  const [tagList, setTagList] = useState([]);
  const [keyword, setKeyword] = useState("");
  const [nextPageList, setNextPageList] = useState([]);

  const navigate = useNavigate();
  const [currentPage, setCurrentPage] = useState(1);
  const [hasPrev, setHasPrev] = useState(false);
  const [hasNext, setHasNext] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    const fetchData = async () => {
      const response = await getChatRoomList(
        tagList,
        keyword,
        currentPage - 1,
        false
      );
      setIsLoading(false);
      if (response.status === 200) {
        setChatRoomList(response.data.chatRoomList);
        setTotalPage(response.data.totalPageNumber);
        const left = Math.floor((currentPage - 1) / 10) * 10 + 1;
        const right = left + 9;
        setNextPageList(
          getArrays(
            Math.max(1, left),
            Math.min(right, response.data.totalPageNumber)
          )
        );
        setHasPrev(currentPage > 10 ? true : false);
        setHasNext(response.data.totalPageNumber > right ? true : false);
        return response;
      }
    };
    fetchData();
  }, [currentPage]);

  const handleJoinChatRoom = (id) => {
    const chatRoom = chatRoomList.find((room) => room.id === id);
    if (chatRoom.type === "PRIVATE_CHAT") {
      const password = prompt("비밀번호를 입력하세요.");
      if (password !== chatRoom.password) {
        alert("비밀번호가 틀렸습니다.");
        return;
      }
      navigate(`/chatRooms/${id}`);
    }
    navigate(`/chatRooms/${id}`);
  };

  const handleLikeButton = async (id) => {
    const chatRoom = chatRoomList.find((room) => room.id === id);
    const current_like = chatRoom.myFavorite;
    const response = await clickLikeButton(id, current_like);
    if (response.status === 200) {
      const newChatRoom = { ...chatRoom };
      newChatRoom.likeCount += current_like === false ? 1 : -1;
      newChatRoom.myFavorite = !chatRoom.myFavorite;
      setChatRoomList((prev) => {
        return prev.map((room) => {
          if (room.id === id) {
            return newChatRoom;
          }
          return room;
        });
      });
    }
  };

  return isLoading ? (
    <Loading />
  ) : (
    <Card>
      {chatRoomList &&
        chatRoomList.map((room) => {
          return (
            <table key={room.id} className="chattable">
              <tbody className="chatbody">
                <tr className="chatrows">
                  <td className="chatid">{room.id}</td>
                  <td
                    className="chattitle"
                    onClick={() => handleJoinChatRoom(room.id)}
                  >
                    {room.title}
                  </td>
                  <td className="chattype">
                    {room.type === "PRIVATE_CHAT" ? <LockIcon /> : ""}
                  </td>
                  <td className="chatlike">
                    <ThumbUpIcon
                      className={`${room.myFavorite ? "favorite_room" : ""}`}
                      onClick={() => handleLikeButton(room.id)}
                    />
                    {room.likeCount}
                  </td>
                </tr>
              </tbody>
            </table>
          );
        })}
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        {hasPrev ? (
          <div
            style={{ display: "flex" }}
            onClick={() =>
              setCurrentPage((c) => (Math.floor((c - 1) / 10) - 1) * 10 + 1)
            }
          >
            <NavigateBeforeIcon />
          </div>
        ) : (
          ""
        )}
        <div>
          <div style={{ display: "flex" }}>
            {nextPageList.length > 0 &&
              nextPageList.map((n, idx) => (
                <div
                  key={idx}
                  style={{
                    padding: "20px",
                    color: n === currentPage ? "red" : "black",
                  }}
                  onClick={() => setCurrentPage(n)}
                >
                  {n}
                </div>
              ))}
          </div>
          {hasNext ? (
            <div
              style={{ display: "flex" }}
              onClick={() =>
                setCurrentPage((c) => (Math.floor((c - 1) / 10) + 1) * 10 + 1)
              }
            >
              <NavigateNextIcon />
            </div>
          ) : (
            ""
          )}
        </div>
      </div>
    </Card>
  );
};

export default Groupchat;
