import React from 'react';
import '../App.css';
import '../static/css/home/home.css';
import { Link } from 'react-router-dom';

import { useLocalState } from '../util/useLocalStorage';

export default function Home() {

    const [jwt,] = useLocalState("jwt", "");

    return (
        <div className="home-page-container">
            <div className="hero-div">
                <h1>!Bienvenido a Dixit!</h1>
                {!jwt ? <h3 className='mt-3'>Inicie sesión para jugar</h3> : <h3 className='mt-3'>!Ya estás listo para jugar!</h3>}

                {!jwt ?
                    <div style={{ display: 'flex', justifyContent: 'space-between', width: 420, marginTop: 15 }}>
                        <Link className="auth-button" to="/register" style={{ textDecoration: "none" }}>
                            Registrarse
                        </Link>

                        <Link className="auth-button" to="/login" style={{ textDecoration: "none" }}>
                            Iniciar Sesión
                        </Link>
                    </div>
                    :
                    <div style={{ display: "flex", alignItems: 'center', justifyContent: "space-around", width: 420, marginTop: 20, textAlign:'center' }}>
                        <Link className="auth-button" to="/games/new" style={{ textDecoration: "none" }}>
                            {`Crear`}<br />{`Partida`}
                        </Link>

                        <Link className="auth-button" to="/games/gameListing" style={{ textDecoration: "none" }}>
                            {`Unirse a`}<br />{`Partida`}
                        </Link>
                    </div>
                }

            </div>
        </div>
    );
}