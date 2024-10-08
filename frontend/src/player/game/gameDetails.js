import { Link } from "react-router-dom";
import { Button, Table } from "reactstrap";
import { useEffect, useState } from "react";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import useFetchState from "../../util/useFetchState";

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function GameDetails() {

  const [message, setMessage] = useState(null);
  const [visible, setVisible] = useState(false);
  const [game, setGame] = useFetchState(
    [],
    `/api/v1/games`,
    jwt,
    setMessage,
    setVisible
  )

  const id = window.location.pathname.split("/")[2];
  useEffect(() => {
    function getGameDetails() {
      fetch(`/api/v1/games/`+ (id == "new" ? "new" : id+"/view"), {
        method: (id !== "new" ? "GET" : "POST"),
        headers: {
          Authorization: `Bearer ${jwt}`,
          Accept: "application/json",
          "Content-Type": "application/json",
        },
      })
        .then((response) => response.json())
        .then((data) => {
          setGame(data);
          console.log(data);
          //window.location.href = "/games/"+data.id+"/view";
        });
    }
    getGameDetails();
  }, [id]);

  return (
    <div className="auth-page-container">

    </div>
  )
}
