import React, { useState } from 'react'
import useFetchState from '../../util/useFetchState';
import tokenService from '../../services/token.service';
import '../../static/css/home/home.css';

export default function CardListing() {
    const jwt = tokenService.getLocalAccessToken();

    const [message, setMessage] = useState("");
    const [visible, setVisible] = useState(false);

    const [cards, setCards] = useFetchState(
        [],
        "/api/v1/cards/listing",
        jwt,
        setMessage,
        setVisible
    );

    const cardList = cards.map(card => {
        return (
            <div key={card.id} style={{margin: 15}}>
                <img src={card.design} alt="Card Design" style={{ height: 250 }} />
            </div>
        );
    });

    return (
        <div style={{ textAlign: 'center', }}>
            <h1 style={{ marginTop: 15 }}>Card Listing</h1><hr />

            <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'center', margin: 25, backgroundColor: "#ffffff4d", borderRadius: 25, padding: "2rem"}}>
                {cardList}
            </div>
        </div>
    )
}
