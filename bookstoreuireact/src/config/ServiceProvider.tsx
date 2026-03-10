import { createContext, useMemo, type ReactNode } from 'react';
import { BookService } from "../services/book-service";
import { createHttp } from './http';

export const BookContext = createContext<BookService | null>(null);

export function ServicesProvider({ children } : {children : ReactNode})  {
  const http = useMemo(() => createHttp(), []);
  const bookService = useMemo(() => new BookService(http), [http]);

  return <BookContext.Provider value={bookService}>{children}</BookContext.Provider>;
}