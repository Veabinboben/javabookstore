import { Route, Routes } from "react-router-dom";
import { Home } from "../components/Home";
import { BookView } from "../components/BookView";
import { BookForm } from "../components/BookForm";
import { StockForm } from "../components/StockForm";
import { ReviewForm } from "../components/ReviewForm";

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/book" element={<BookView />} />
      <Route path="/book/add" element={<BookForm />} />
      <Route path="/book/edit" element={<BookForm />} />
      <Route path="/stock/add" element={<StockForm />} />
      <Route path="/review/add" element={<ReviewForm />} />
    </Routes>
  );
}