import type { AxiosInstance } from 'axios';
import type { Review } from '../models/review';
import type { Page } from '../models/page';

export class ReviewService {
    private http: AxiosInstance;

    constructor(http: AxiosInstance) {
        this.http = http;
    }

    async getReviews(page: number, pageSize: number, bookId: number): Promise<Page<Review>> {
        const { data } = await this.http.get<Page<Review>>('/reviews/all', {
            params: {
                page: page,
                pageSize: pageSize,
                bookId: bookId
            }
        });
        return data;
    }

    async addReview(form: FormData): Promise<Review> {
        const { data } = await this.http.post<Review>('/reviews/save', form, {
            headers: {
                'Content-Type': undefined,
            },
        });
        return data;
    }
}