import { useEffect, useState } from "react";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";

const jwt = tokenService.getLocalAccessToken();

export default function GameDetails() {

    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [game, setGame] = useState([]);

    const id = window.location.pathname.split("/")[2];

    useEffect(() => {
        function createGame(){
            fetch(`/api/v1/games/new`, {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    Accept: "application/json",
                    "Content-Type": "application/json",
                },
            })
                .then((response) => {
                    if (response.status === 200) return response.json();
                    else throw new Error("Error retrieving game details");
                })
                .then((data) => {
                    setGame(data);
                })
                .catch((error) => {
                    setMessage(error.message);
                    setVisible(true);
                });
        }
        createGame();
    }, []);

    // useEffect(() => {
    //   function getGameDetails() {
    //     if (id !== 'new') {
    //       fetch(`/api/v1/games/${id}`, {
    //         headers: {
    //           Authorization: `Bearer ${jwt}`,
    //           Accept: "application/json",
    //           "Content-Type": "application/json",
    //         },
    //       })
    //         .then((response) => {
    //           if (response.status === 200) return response.json();
    //           else throw new Error("Error retrieving game details");
    //         })
    //         .then((data) => {
    //           setGame(data);
    //         })
    //         .catch((error) => {
    //           setMessage(error.message);
    //           setVisible(true);
    //         });
    //     } else {
    //       fetch(`/api/v1/games/new`, {
    //         headers: {
    //           Authorization: `Bearer ${jwt}`,
    //           Accept: "application/json",
    //           "Content-Type": "application/json",
    //         },
    //       })
    //         .then((response) => {
    //           if (response.status === 200) return response.json();
    //           else throw new Error("Error retrieving game details");
    //         })
    //         .then((data) => {
    //           setGame(data);
    //         })
    //         .catch((error) => {
    //           setMessage(error.message);
    //           setVisible(true);
    //         });
    //     }
    //   }
    //   getGameDetails();
    // }, [id]);

    return (
        <div className="auth-page-container">

        </div>
    )
}
