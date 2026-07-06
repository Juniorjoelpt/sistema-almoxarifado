import { motion } from "framer-motion";
import {
FaBoxes,
FaTruck,
FaExchangeAlt,
FaExclamationTriangle,
FaCheckCircle
} from "react-icons/fa";



function ProdutoInfoCard({ produto, quantidadeRecebida }) {

    if (!produto) return null;
const quantidadeConvertida =

    quantidadeRecebida *

    (produto.fatorConversao || 1);

const novoEstoque =

    Number(produto.estoqueAtual)

    +

    quantidadeConvertida;

const estoqueCritico =
produto.estoqueAtual <= produto.estoqueMinimo;

const situacao =

novoEstoque <= produto.estoqueMinimo

? "critico"

: novoEstoque <= produto.estoqueMinimo * 1.5

? "alerta"

: "ok";

    return (

        <motion.div

    initial={{
        opacity:0,
        y:20,
        scale:0.98
    }}

    animate={{
        opacity:1,
        y:0,
        scale:1
    }}

    transition={{
        duration:0.35
    }}

    className="card shadow border-0 mb-3"

>

            <div
    className="card-header d-flex justify-content-between align-items-center bg-primary text-white"
>

<div>

<h5 className="mb-0">

📦 {produto.nome}

</h5>

<small>

Código:

{produto.codigo}

</small>

</div>

<span className="badge bg-light text-dark">

{produto.categoria?.nome}

</span>

</div>

            <div className="card-body">

                <div className="row">

                    <div className="card-body">

    <div className="row">

<div className="col-md-6 mb-3">

<div className="border rounded p-3">

<FaBoxes
className="text-primary me-2"
/>

<strong>

Estoque Atual

</strong>

<h4>

{produto.estoqueAtual}

{" "}

{produto.unidadeEstoque}

</h4>

</div>

</div>





<div className="row mt-3">

<div className="col-md-4">

<div className="border rounded p-3">

<strong>

Estoque Atual

</strong>

<h4>

{produto.estoqueAtual}

{" "}

{produto.unidadeEstoque}

</h4>

</div>

</div>

<div className="col-md-4">

<div className="border rounded p-3">

<strong>

Entrada

</strong>

<h4>

{quantidadeRecebida}

{" "}

{produto.unidadeEmbalagem}

</h4>

</div>

</div>

<div className="col-md-4">

<div className="border rounded p-3">

<strong>

Conversão

</strong>

<h4>

{quantidadeConvertida}

{" "}

{produto.unidadeEstoque}

</h4>

</div>

</div>

</div>



<div className="card mt-3 border-success">

<div className="card-body text-center">

<h6>

Novo Estoque

</h6>

<h2>

{novoEstoque}

{" "}

{produto.unidadeEstoque}

</h2>

</div>

</div>




{

situacao === "ok" && (

<div className="alert alert-success mt-3">

✅ Estoque ficará acima do mínimo.

</div>

)

}




{

situacao === "alerta" && (

<div className="alert alert-warning mt-3">

⚠ Estoque ficará próximo do mínimo.

</div>

)

}





{

situacao === "critico" && (

<div className="alert alert-danger mt-3">

🚨 Mesmo após esta entrada o estoque continuará crítico.

</div>

)

}


<motion.h2

key={novoEstoque}

initial={{

scale:0.7,

opacity:0

}}

animate={{

scale:1,

opacity:1

}}

transition={{

duration:0.3

}}

>

{novoEstoque}

{" "}

{produto.unidadeEstoque}

</motion.h2>







<div className="col-md-6 mb-3">

<div className="border rounded p-3">

<FaExclamationTriangle
className="text-warning me-2"
/>

<strong>

Estoque Mínimo

</strong>

<h4>

{produto.estoqueMinimo}

{" "}

{produto.unidadeEstoque}

</h4>

</div>

</div>

</div>

</div>
<div className="border rounded p-3 mt-2">

<FaExchangeAlt
className="text-success me-2"
/>

<strong>

Conversão

</strong>

<div className="fs-5">

1

{" "}

{produto.unidadeEmbalagem}

{" = "}

<strong>

{produto.fatorConversao}

{" "}

{produto.unidadeEstoque}

</strong>

</div>

</div>

<div className="mt-3">

{

estoqueCritico ?

<div className="alert alert-danger">

⚠ Estoque abaixo do mínimo.

</div>

:

<div className="alert alert-success">

<FaCheckCircle className="me-2"/>

Estoque em nível adequado.

</div>

}

</div>

                </div>

            </div>

        </motion.div>

    );

}

export default ProdutoInfoCard;