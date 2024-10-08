import { Link } from "react-router-dom";
import { Button, Table } from "reactstrap";
import { useState } from "react";
import tokenService from "../../services/token.service";
import useFetchState from "../../util/useFetchState";
import "../../static/css/auth/authButton.css";
import "../../static/css/auth/authPage.css";

const jwt = tokenService.getLocalAccessToken();
const user = tokenService.getUser();

export default function GameListing() {

    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [games, setGames] = useFetchState(
        [],
        `/api/v1/games`,
        jwt,
        setMessage,
        setVisible
    )

    console.log(games);

    const gameList = games.map((game) => {
        return (
            <tr key={game.id}>
                <td style={{ whiteSpace: "nowrap" }}>
                    {game.id}
                </td>
                <td>{game.numberOfPlayers}/4</td>
                <td>{game.scoreboards.get(0).user.username}</td>
                <td>
                    <Button>
                        <Link to={`/games/${game.id}/join/${user.id}`}>Join</Link>
                    </Button>
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
                            <th>Host</th>
                        </tr>
                    </thead>
                    <tbody>
                        {gameList}
                    </tbody>
                </Table>
            </div>
                <Link className="auth-button" style={{textDecoration: "none"}} to="/games/create">Create Game</Link>
                <Link className="auth-button" style={{textDecoration: "none"}} to="/">Volver</Link>
        </div>
    );
}