import React from "react";
import { Route, Routes } from "react-router-dom";
import jwt_decode from "jwt-decode";
import { ErrorBoundary } from "react-error-boundary";
import AppNavbar from "./AppNavbar";
import Home from "./home";
import PrivateRoute from "./privateRoute";
import PricingPlan from "./owner/plan";
import Register from "./auth/register";
import Login from "./auth/login";
import Logout from "./auth/logout";
import OwnerPetList from "./owner/pets/petList";
import OwnerPetEdit from "./owner/pets/petEdit";
import OwnerVisitEdit from "./owner/visits/visitEdit";
import PlanList from "./public/plan";
import tokenService from "./services/token.service";
import OwnerDashboard from "./owner/dashboard";
import OwnerConsultationList from "./owner/consultations/consultationList";
import OwnerConsultationEdit from "./owner/consultations/consultationEdit";
import OwnerConsultationTickets from "./owner/consultations/tickets/ticketList";
import VetConsultationList from "./vet/consultations/consultationList";
import VetConsultationTickets from "./vet/consultations/tickets/ticketList";
import UserListAdmin from "./admin/users/UserListAdmin";
import UserEditAdmin from "./admin/users/UserEditAdmin";
import SwaggerDocs from "./public/swagger";
import ClinicsList from "./clinicOwner/clinicsList"
import EditClinic from "./clinicOwner/clinicEdit"
import OwnerListClinicOwner from "./clinicOwner/ownersList"
import ConsultationEditClinicOwner from "./clinicOwner/consultations/ConsultationEditClinicOwner";
import VetListClinicOwner from "./clinicOwner/vets/VetListClinicOwner";
import VetEditClinicOwner from "./clinicOwner/vets/VetEditClinicOwner";
import ConsultationListClinicOwner from "./clinicOwner/consultations/ConsultationListClinicOwner";

import GameListing from "./player/game/gameListing";
import GameDetails from "./player/game/gameDetails";
import CardListing from "./player/card/CardListing";

function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div role="alert">
      <p>Something went wrong:</p>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  )
}

function App() {
  const jwt = tokenService.getLocalAccessToken();
  let roles = []
  if (jwt) {
    roles = getRolesFromJWT(jwt);
  }

  function getRolesFromJWT(jwt) {
    return jwt_decode(jwt).authorities;
  }

  let adminRoutes = <></>;
  let ownerRoutes = <></>;
  let userRoutes = <></>;
  let vetRoutes = <></>;
  let publicRoutes = <></>;
  let playerRoutes = <></>;

  roles.forEach((role) => {
    if (role === "ADMIN") {
      adminRoutes = (
        <>
          <Route path="/users" exact={true} element={<PrivateRoute><UserListAdmin /></PrivateRoute>} />
          <Route path="/users/:username" exact={true} element={<PrivateRoute><UserEditAdmin /></PrivateRoute>} />
          <Route path="/cards" exact={true} element={<PrivateRoute><CardListing /></PrivateRoute>} />
          <Route path="/games/gameListing" exact={true} element={<PrivateRoute><GameListing /></PrivateRoute>} />
          <Route path="/games/:id" exact={true} element={<PrivateRoute><GameDetails /></PrivateRoute>} />)
        </>)
    }
    if (role === "OWNER") {
      ownerRoutes = (
        <>
          <Route path="/dashboard" element={<PrivateRoute><OwnerDashboard /></PrivateRoute>} />
          <Route path="/plan" exact={true} element={<PrivateRoute><PricingPlan /></PrivateRoute>} />
          <Route path="/myPets" exact={true} element={<PrivateRoute><OwnerPetList /></PrivateRoute>} />
          <Route path="/myPets/:id" exact={true} element={<PrivateRoute><OwnerPetEdit /></PrivateRoute>} />
          <Route path="/myPets/:id/visits/:id" exact={true} element={<PrivateRoute><OwnerVisitEdit /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><OwnerConsultationList /></PrivateRoute>} />
          <Route path="/consultations/:consultationId" exact={true} element={<PrivateRoute><OwnerConsultationEdit /></PrivateRoute>} />
          <Route path="/consultations/:consultationId/tickets" exact={true} element={<PrivateRoute><OwnerConsultationTickets /></PrivateRoute>} />
        </>)
    }
    if (role === "VET") {
      vetRoutes = (
        <>
          {/* <Route path="/dashboard" element={<PrivateRoute><OwnerDashboard /></PrivateRoute>} /> */}
          <Route path="/myPets" exact={true} element={<PrivateRoute><OwnerPetList /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><VetConsultationList /></PrivateRoute>} />
          <Route path="/consultations/:consultationId/tickets" exact={true} element={<PrivateRoute><VetConsultationTickets /></PrivateRoute>} />
        </>)
    }
    if (role === "CLINIC_OWNER") {
      vetRoutes = (
        <>
          <Route path="/owners" exact={true} element={<PrivateRoute><OwnerListClinicOwner /></PrivateRoute>} />
          <Route path="/clinics" exact={true} element={<PrivateRoute><ClinicsList /></PrivateRoute>} />
          <Route path="/clinics/:id" exact={true} element={<PrivateRoute><EditClinic /></PrivateRoute>} />
          <Route path="/consultations" exact={true} element={<PrivateRoute><ConsultationListClinicOwner /></PrivateRoute>} />
          <Route path="/consultations/:id" exact={true} element={<PrivateRoute><ConsultationEditClinicOwner /></PrivateRoute>} />
          <Route path="/consultations/:id/tickets" exact={true} element={<PrivateRoute><VetConsultationTickets /></PrivateRoute>} />
          <Route path="/vets" exact={true} element={<PrivateRoute><VetListClinicOwner /></PrivateRoute>} />
          <Route path="/vets/:id" exact={true} element={<PrivateRoute><VetEditClinicOwner /></PrivateRoute>} />
        </>)
    }
    if (role === "PLAYER") {
      playerRoutes = (
        <>
          <Route path="/cards" exact={true} element={<PrivateRoute><CardListing /></PrivateRoute>} />
          <Route path="/games/gameListing" exact={true} element={<PrivateRoute><GameListing /></PrivateRoute>} />
          <Route path="/games/:id" exact={true} element={<PrivateRoute><GameDetails /></PrivateRoute>} />)
        </>
      )
    }
  })
  if (!jwt) {
    publicRoutes = (
      <>
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  } else {
    userRoutes = (
      <>
        {/* <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} /> */}
        <Route path="/logout" element={<Logout />} />
        <Route path="/login" element={<Login />} />
      </>
    )
  }

  return (
    <div>
      <ErrorBoundary FallbackComponent={ErrorFallback} >
        <AppNavbar />
        <Routes>
          <Route path="/" exact={true} element={<Home />} />
          <Route path="/plans" element={<PlanList />} />
          <Route path="/docs" element={<SwaggerDocs />} />
          {publicRoutes}
          {userRoutes}
          {adminRoutes}
          {ownerRoutes}
          {vetRoutes}
          {playerRoutes}
        </Routes>
      </ErrorBoundary>
    </div>
  );
}

export default App;
