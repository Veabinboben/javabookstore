import { BrowserRouter, Link, Outlet } from "react-router-dom";
import { AppRoutes } from "./AppRoutes";


export function RouterShell() {
    return (
        <>
            <BrowserRouter>
                <nav>
                    <Link to={{ pathname: '/', search: new URLSearchParams({ page: '1' }).toString() }}>Home</Link> |{" "}
                    <Link to="/asd">asd</Link> |{" "}
                </nav>
                <AppRoutes></AppRoutes>
            </BrowserRouter>

            <Outlet />

            {/* TODO */}
            <footer>sigmo</footer>
        </>
    )
};