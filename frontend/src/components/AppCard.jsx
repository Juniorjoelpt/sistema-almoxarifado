import "./AppCard.css";

function AppCard({

    children,
    className = "",
    style = {}

}) {

    return (

        <div
            className={`app-card ${className}`}
            style={style}
        >

            {children}

        </div>

    );

}

export default AppCard;