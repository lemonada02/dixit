import React, { useState } from 'react'
import useFetchState from '../../util/useFetchState';
import { Table } from 'reactstrap';
import tokenService from '../../services/token.service';

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
            <tr key={card.id}>
                <td>{card.id}</td>
                <td>
                    <img src={card.design} alt="Card Design" style={{height: 80}} />
                </td>
            </tr>
        );
    });

    return (
        <div>
            <h1>Card Listing</h1><hr />

            <Table>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Card Design</th>
                    </tr>
                </thead>
                <tbody>
                    {cardList}
                </tbody>
            </Table>
        </div>
    )
}
