import React, { useEffect, useState } from 'react';
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";

const jwt = tokenService.getLocalAccessToken();

export default function GameDetails() {
    const titulo = <h1>Game Details</h1>;
    const [game, setGame] = useState({});
    const id = window.location.pathname.split("/")[2];


    useEffect(() => {
        const fetchGame = async () => {
            const response = await fetch(`/api/v1/games/new`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${jwt}`,
                    "Content-Type": "application/json",
                },
            });
            const data = await response.json();
            setGame(data);
        };
        fetchGame();
    }, []);

    return (
        <div className="auth-page-container">
            {titulo}
            {game.id}
        </div>
    );
}