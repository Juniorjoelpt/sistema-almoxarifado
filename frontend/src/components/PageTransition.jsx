import { useEffect, useState } from "react";

export default function PageTransition({ children }) {

    const [show, setShow] = useState(false);

    useEffect(() => {
        setShow(true);
    }, []);

    return (
        <div className="page-wrapper">
            <div className={show ? "page-enter-active" : "page-enter"}>
                {children}
            </div>
        </div>
    );
}