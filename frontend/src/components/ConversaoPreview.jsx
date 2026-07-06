import { FaArrowDown, FaBoxes } from "react-icons/fa";

function ConversaoPreview({

    unidadeCompra,
    unidadeEstoque,
    fatorConversao

}) {

    if (

        !unidadeCompra ||
        !unidadeEstoque ||
        !fatorConversao

    ) {

        return null;

    }

    return (

        <div
            className="card shadow-sm border-0 mt-3"
            style={{
                animation: "fadeIn .35s"
            }}
        >

            <div className="card-body">

                <h6 className="mb-4">

                    <FaBoxes className="me-2 text-success"/>

                    Conversão do Estoque

                </h6>

                <div
                    className="d-flex
                               justify-content-center
                               align-items-center
                               flex-column"
                >

                    <div
                        className="fs-4
                                   fw-bold
                                   text-primary"
                    >

                        1 {unidadeCompra}

                    </div>

                    <FaArrowDown
                        className="my-3
                                   fs-2
                                   text-success"
                    />

                    <div
                        className="fs-3
                                   fw-bold
                                   text-success"
                    >

                        {fatorConversao} {unidadeEstoque}

                    </div>

                </div>

                <hr/>

                <div className="text-center text-muted">

                    O estoque será armazenado em

                    <strong>

                        {" "}
                        {unidadeEstoque}

                    </strong>

                </div>

            </div>

        </div>

    );

}

export default ConversaoPreview;