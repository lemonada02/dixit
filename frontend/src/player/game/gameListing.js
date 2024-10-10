import { Link } from "react-router-dom";
import { Table } from "reactstrap";
import { useState } from "react";
import tokenService from "../../services/token.service";
import useFetchState from "../../util/useFetchState";
import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";

const jwt = tokenService.getLocalAccessToken();

export default function GameListing() {
    const [message, setMessage] = useState("");
    const [visible, setVisible] = useState(false);

    const [games, setGames] = useFetchState(
        [],
        "/api/v1/games/gameListing",
        jwt,
        setMessage,
        setVisible
    );

    const gameList = games.map(game => {
        return (
            <tr key={game.id}>
                <td>{game.id}</td>
                <td>{game.numberOfPlayers}/4</td>
                <td>{game.creator}</td>
                <td>
                    <Link className="auth-button" style={{textDecoration: "none"}} to={`/games/${game.id}`}>Join</Link>
                </td>
            </tr>
        );
    });

    return (
        <div style={{margin: 15, textAlign: "center"}}>
            <div className="admin-page-container">
                <Table>
                    <thead>
                        <tr>
                            <th>Game ID</th>
                            <th>Players</th>
                            <th>Creator</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {gameList}
                    </tbody>
                </Table>
            </div>
                <Link className="auth-button" style={{textDecoration: "none"}} to="/games/new">Create Game</Link>
                <Link className="auth-button" style={{textDecoration: "none"}} to="/">Volver</Link>
        </div>
    );
}