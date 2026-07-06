import AppCard from "./AppCard";

function StatCard({

    titulo,
    valor,
    cor = "dark"

}) {

    return (

        <AppCard>

            <h6
                className="fw-bold text-dark mb-2"
            >
                {titulo}
            </h6>

            <h2
                className={`text-${cor} fw-bold mb-0`}
            >
                {valor}
            </h2>

        </AppCard>

    );

}

export default StatCard;