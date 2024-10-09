import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';

class VisitEdit extends Component {

    emptyVisit = {
        id: '',
        datetime: '',
        description: '',
        pet: {},
    };

    constructor(props) {
        super(props);
        this.state = {
            visit: this.emptyVisit,
            pet: {},
            message: null,
            modalShow: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleShow = this.handleShow.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.jwt = JSON.parse(window.localStorage.getItem("jwt"));

        let pathArray = window.location.pathname.split('/');
        this.petId = pathArray[2];
        this.visitId = pathArray[4];
    }

    async componentDidMount() {
        const pet = await (await fetch(`/api/v1/pets/${this.petId}`, {
            headers: {
                "Authorization": `Bearer ${this.jwt}`,
            },
        })).json();
        if (pet.message) this.setState({ message: pet.message, modalShow: true });
        else this.setState({ pet: pet });

        if (!this.state.message) {
            if (this.visitId !== 'new' && !this.state.message) {
                const visit = await (await fetch(`/api/v1/pets/${this.petId}/visits/${this.visitId}`, {
                    headers: {
                        "Authorization": `Bearer ${this.jwt}`,
                    },
                })).json();
                if (visit.message) this.setState({ message: visit.message, modalShow: true });
                else this.setState({ visit: visit });
            }
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let visit = { ...this.state.visit };
        visit[name] = value;
        this.setState({ visit });
    }

    handleShow() {
        let modalShow = this.state.modalShow;
        this.setState({ modalShow: !modalShow });
    }

    async handleSubmit(event) {
        event.preventDefault();
        let visit = { ...this.state.visit };
        const pet = { ...this.state.pet };
        visit["pet"] = pet;

        const submit = await (await fetch(`/api/v1/pets/${this.petId}/visits` + (visit.id ? '/' + visit.id : ''), {
            method: (visit.id) ? 'PUT' : 'POST',
            headers: {
                "Authorization": `Bearer ${this.jwt}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(visit),
        })).json();
        if (submit.message) this.setState({ message: submit.message, modalShow: true });
        else window.location.href = `/pets/${this.petId}/visits`;

    }

    render() {
        const { visit, pet} = this.state;
        const title = <h2>{visit.id ? 'Edit Visit' : 'Add Visit'}</h2>;

        let modal = <></>;
        if (this.state.message) {
            const show = this.state.modalShow;
            const closeBtn = (
                <button className="close" onClick={this.handleShow} type="button">
                    &times;
                </button>
            );
            modal = <div>
                <Modal isOpen={show} toggle={this.handleShow}
                    keyboard={false}>
                    <ModalHeader toggle={this.handleShow} close={closeBtn}>Error!</ModalHeader>
                    <ModalBody>
                        {this.state.message || ""}
                    </ModalBody>
                    <ModalFooter>
                        <Button color="info" onClick={this.handleShow} type="button">Close</Button>
                        <Button color="primary" tag={Link} to={`/myPets`}>Back</Button>
                    </ModalFooter>
                </Modal></div>
        }

        return <div>
            {/* <AppNavbar /> */}
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="datetime">Date and Time</Label>
                        <Input type="datetime-local" required name="datetime" id="datetime" value={visit.datetime || ''}
                            onChange={this.handleChange} autoComplete="datetime" />
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" value={visit.description || ''}
                            onChange={this.handleChange} autoComplete="description" />
                    </FormGroup>
                    <FormGroup>
                        <Label for="pet">Pet</Label>
                        <p>{pet.name || ''}</p>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to={`/pets/${this.petId}/visits`}>Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
            {modal}
        </div >
    }
}
export default VisitEdit;