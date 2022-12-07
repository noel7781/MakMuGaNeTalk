import { useEffect, useRef } from "react";
import "../css/chatRoom.css";

const ChatPresenter = ({
  contents,
  message,
  setMessage,
  handleSubmit,
  userId,
}) => {
  const messagesEndRef = useRef(null);

  const handleEnter = (e) => {
    if (e.keyCode == 13)
      if (!e.shiftKey) {
        e.preventDefault();
        handleSubmit(e);
      }
  };

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [contents]);

  return (
    <div className="outline">
      <div className="messageBox">
        {contents.map((message, idx) => (
          <div
            className={`messages ${
              message.email === userId ? "my-chat" : "other-chat"
            }`}
            key={idx}
          >
            {/* {message.nickname} : {message.content} At {message.createdAt} */}

            <div
              className={
                message.email === userId ? "my-chat-inner" : "other-chat-inner"
              }
            >
              <p>{message.content} </p>
              <span className="time_date"> {message.createdAt}</span>
            </div>
          </div>
        ))}
        <div ref={messagesEndRef} />
      </div>
      <form className="send" onSubmit={handleSubmit}>
        <textarea
          className="send_text"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyDown={handleEnter}
        ></textarea>
        <button className="send_button" type="submit">
          전송
        </button>
      </form>
    </div>
  );
};

export default ChatPresenter;
