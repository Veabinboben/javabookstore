import { Route, Routes } from "react-router-dom";
import { Home } from "../components/Home";
import { Book } from "../components/Book";

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/book" element={<Book />} />
    </Routes>
  );
}