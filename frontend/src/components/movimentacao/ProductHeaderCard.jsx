import {
    FaBoxOpen,
    FaTag,
    FaBarcode,
    FaRulerCombined
} from "react-icons/fa";

function ProductHeaderCard({ produto }) {

    if (!produto) return null;

    return (

        <div className="card shadow-sm border-0 mb-3">

            <div className="card-body">

                <div className="d-flex justify-content-between align-items-center">

                    <div>

                        <h4 className="mb-1">

                            <FaBoxOpen
                                className="text-warning me-2"
                            />

                            {produto.nome}

                        </h4>

                        <div className="text-muted">

                            <FaBarcode className="me-1"/>

                            Código:

                            <strong className="ms-1">

                                {produto.codigo}

                            </strong>

                        </div>

                    </div>

                    <span
                        className="badge bg-primary fs-6"
                    >

                        <FaTag className="me-1"/>

                        {produto.categoria?.nome}

                    </span>

                </div>

                <hr/>

                <div className="row">

                    <div className="col-md-4">

                        <small className="text-muted">

                            Unidade

                        </small>

                        <h5>

                            <FaRulerCombined
                                className="me-2"
                            />

                            {produto.unidade}

                        </h5>

                    </div>

                    <div className="col-md-4">

                        <small className="text-muted">

                            Estoque Atual

                        </small>

                        <h5>

                            {produto.estoqueAtual}

                        </h5>

                    </div>

                    <div className="col-md-4">

                        <small className="text-muted">

                            Estoque Mínimo

                        </small>

                        <h5>

                            {produto.estoqueMinimo}

                        </h5>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default ProductHeaderCard;