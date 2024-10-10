import { useEffect, useState } from "react";
import tokenService from "../../services/token.service";
import "../../static/css/admin/adminPage.css";
import useFetchState from "../../util/useFetchState";

const jwt = tokenService.getLocalAccessToken();

export default function GameDetails() {
    const [titulo, setTitulo] = useState("Game");
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [game, setGame] = useFetchState(
        {},
        "/api/v1/games/new",
        jwt,
        setMessage,
        setVisible
    );
    const id = window.location.pathname.split("/")[2];


    useEffect(() => {

    }, [game]);


    return (
        <div className="auth-page-container">
            {titulo}
        </div>
    )
}
