import { createContext, useMemo, type ReactNode } from 'react';
import { BookService } from "../services/book-service";
import { createHttp } from './http';
import { AuthorService } from '../services/author-service';
import { GenreService } from '../services/genre-service';
import { PublisherService } from '../services/publisher-service';
import { StockService } from '../services/stock-service';
import { ReviewService } from '../services/review-service';
import { WarehouseService } from '../services/warehouse-service';

export const BookContext = createContext<BookService | null>(null);
export const AuthorContext = createContext<AuthorService | null>(null);
export const GenreContext = createContext<GenreService | null>(null);
export const PublisherContext = createContext<PublisherService | null>(null);
export const StockContext = createContext<StockService | null>(null);
export const ReviewContext = createContext<ReviewService | null>(null);
export const WarehouseContext = createContext<WarehouseService | null>(null);

export function ServicesProvider({ children }: { children: ReactNode }) {
  const http = useMemo(() => createHttp(), []);
  const bookService = useMemo(() => new BookService(http), [http]);
  const authorService = useMemo(() => new AuthorService(http), [http]);
  const genreService = useMemo(() => new GenreService(http), [http]);
  const publisherService = useMemo(() => new PublisherService(http), [http]);
  const stockService = useMemo(() => new StockService(http), [http]);
  const reviewService = useMemo(() => new ReviewService(http), [http]);
  const warehouseService = useMemo(() => new WarehouseService(http), [http]);

  return <BookContext.Provider value={bookService}>
    <AuthorContext.Provider value={authorService}>
    <GenreContext.Provider value={genreService}>
    <PublisherContext.Provider value={publisherService}>
    <StockContext.Provider value={stockService}>
    <ReviewContext.Provider value={reviewService}>
    <WarehouseContext.Provider value={warehouseService}>
    {children}
    </WarehouseContext.Provider>  
    </ReviewContext.Provider>
    </StockContext.Provider>
    </PublisherContext.Provider>
    </GenreContext.Provider>
    </AuthorContext.Provider>
    </BookContext.Provider>;
}