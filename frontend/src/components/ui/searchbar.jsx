import { useState } from "react";

const SearchBar = () => {
  const [userInput, setUserInput] = useState("");
  const [searchState, setSearchState] = useState("keyword");

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(e);
  };
  const handleUserInput = (e) => {
    setUserInput(e.target.value);
  };
  const onClickKeywordButton = () => {
    setSearchState("keyword");
  };
  const onClickTagButton = () => {
    setSearchState("tag");
  };

  return (
    <div style={{ display: "flex" }}>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={userInput}
          onChange={handleUserInput}
          placeholder="검색"
        />
        <button onClick={onClickKeywordButton}>키워드</button>
        <button onClick={onClickTagButton}>태그</button>
        <button type="submit">검색</button>
      </form>
    </div>
  );
};

export default SearchBar;
