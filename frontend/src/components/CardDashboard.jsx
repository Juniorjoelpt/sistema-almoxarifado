function CardDashboard({
  titulo,
  valor,
  icone,
  cor = "#d39a32"
}) {

  return (

    <div className="col-md-3 mb-4 dashboard-fade-in">

      <div
        className="card border-0 shadow-sm h-100"
        style={{
          borderRadius: "18px"
        }}
      >

        <div className="card-body">

          <div className="d-flex justify-content-between align-items-center">

            <div>

              <small className="dashboard-card-title">
                {titulo}
              </small>

              <h2
                className="dashboard-card-value"
                style={{
                  color: cor
                }}
              >
                {valor}
              </h2>

            </div>

            <div 
              style={{
                fontSize: "32px",
                color: cor
              }}
            >
              {icone}
            </div>

          </div>

        </div>

      </div>

    </div>

  );
}

export default CardDashboard;