import {
    FaBoxes,
    FaExclamationTriangle,
    FaBalanceScale,
    FaExchangeAlt
} from "react-icons/fa";

function EstoqueResumo({ produto }) {

    if (!produto) return null;

    return (

        <div className="row mb-3">

            <div className="col-md-3">

                <div className="card shadow-sm border-0 h-100">

                    <div className="card-body">

                        <small className="text-muted">

                            <FaBoxes className="me-2"/>

                            Estoque Atual

                        </small>

                        <h3 className="mt-2">

                            {produto.estoqueAtual}

                        </h3>

                        <span className="text-secondary">

                            {produto.unidadeEstoque}

                        </span>

                    </div>

                </div>

            </div>

            <div className="col-md-3">

                <div className="card shadow-sm border-0 h-100">

                    <div className="card-body">

                        <small className="text-muted">

                            <FaExclamationTriangle className="me-2 text-warning"/>

                            Estoque Mínimo

                        </small>

                        <h3 className="mt-2">

                            {produto.estoqueMinimo}

                        </h3>

                        <span className="text-secondary">

                           {produto.unidadeEstoque}

                        </span>

                    </div>

                </div>

            </div>

            <div className="col-md-3">

                <div className="card shadow-sm border-0 h-100">

                    <div className="card-body">

                        <small className="text-muted">

                            <FaBalanceScale className="me-2"/>

                            Unidade Base

                        </small>

                        <h3 className="mt-2">

                            {produto.unidadeEstoque}

                        </h3>

                    </div>

                </div>

            </div>

            <div className="col-md-3">

                <div className="card shadow-sm border-0 h-100">

                    <div className="card-body">

                        <small className="text-muted">

                            <FaExchangeAlt className="me-2 text-success"/>

                            Conversão

                        </small>

                        <h5 className="mt-3">

                            {

				produto.unidadeCompra &&
				produto.unidadeEstoque &&
				produto.fatorConversao > 1

					?

			`1 ${produto.unidadeCompra} = ${produto.fatorConversao} ${produto.unidadeEstoque}`

				:

			"Sem conversão"

				}

                        </h5>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default EstoqueResumo;