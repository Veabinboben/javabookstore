import { Route, Routes } from "react-router-dom";
import { Home } from "../components/Home";
import { BookView } from "../components/BookView";
import { BookForm } from "../components/BookForm";

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/book" element={<BookView />} />
      <Route path="/book/add" element={<BookForm />} />
    </Routes>
  );
}