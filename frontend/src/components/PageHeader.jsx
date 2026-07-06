import "./PageHeader.css";

function PageHeader({

    categoria,
    titulo,
    subtitulo,
    botaoTexto,
    onClick

}){

    return(

        <div className="page-header">

            <div>

                {

                    categoria && (

                        <div className="page-category">

                            {categoria}

                        </div>

                    )

                }

                <h1>

                    {titulo}

                </h1>

                {

                    subtitulo && (

                        <p>

                            {subtitulo}

                        </p>

                    )

                }

            </div>

            {

                botaoTexto && (

                    <button

                        className="btn btn-gold"

                        onClick={onClick}

                    >

                        + {botaoTexto}

                    </button>

                )

            }

        </div>

    );

}

export default PageHeader;