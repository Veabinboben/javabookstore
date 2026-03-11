import { useContext, useEffect, useState } from "react";
import { StockContext, WarehouseContext } from "../config/ServiceProvider";
import AsyncSelect from "react-select/async";
import type { SingleValue } from "react-select";
import { useLocation, useNavigate } from "react-router-dom";
import styles from './Form.module.css';
import type { Warehouse } from "../models/warehouse";

export function StockForm() {
    const stockService = useContext(StockContext);
    const warehouseService = useContext(WarehouseContext);
    const location = useLocation();
    const navigate = useNavigate();

    const [submitting, setSubmitting] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const [warehouses, setWarehouses] = useState<Warehouse[]>([]);
    
    const [selectedWarehouse, setSelectedWarehouse] = useState<Warehouse| null>(null);
    

    const params = new URLSearchParams(location.search);
    const id = Number(params.get("id") ?? -1);
    
    useEffect(() => {
        warehouseService?.getWarehouses('').then((w) => setWarehouses(w));
    },[]);

    async function addStock(formData: FormData) {
        setSubmitting(true);
        setError(null);
        setSuccess(false);     
        try {

            formData.set('bookId', id.toString());
            formData.set('warehouseId', selectedWarehouse!.id.toString());

            await stockService?.addStock(formData);

            const next = new URLSearchParams();
            next.set('id', id.toString());
            navigate({ pathname: '/book', search: next.toString() }, { replace: true })

            setSuccess(true);
        } catch (err) {
            setError("Failed to add book. Please try again.");
        } finally {
            setSubmitting(false);
        }
    }


   
    
    const loadWarehouses = (input : string) => {
        return warehouseService?.getWarehouses(input || "");
    }
    const selectWarehouse = (selected : SingleValue<Warehouse>) => {
        //console.log(selected);
        setSelectedWarehouse(selected);
    }

    return (
        <form action={addStock} encType="multipart/form-data">
            <input type="number"  name="stock" placeholder="Stock" required />
            <AsyncSelect 
                placeholder ={'Select warehouse'}
                defaultOptions={warehouses}
                onChange={selectWarehouse}
                loadOptions={loadWarehouses}
                value={selectedWarehouse}
                getOptionValue={(opt) => String(opt.id)}
                getOptionLabel={(opt) => opt.adress}
            />
            <button type="submit" disabled={submitting}>
                {submitting ? "Adding..." : "Add stock"}
            </button>
            {success && <p>Stock added successfully!</p>}
            {error && <p>{error}</p>}
        </form>
    );
}