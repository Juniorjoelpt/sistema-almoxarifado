import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import { FaArrowUp } from "react-icons/fa";
import PageHeader from "../components/PageHeader";
import { Link, useNavigate } from "react-router-dom";

function NovaSaida() {

    const navigate = useNavigate();

    const [produtos, setProdutos] = useState([]);

    const [setores, setSetores] = useState([]);

    const [movimentacao, setMovimentacao] = useState({

    produto: {
        id: ""
    },

    setor: {
        id: ""
    },

    tipo: "SAIDA",

    quantidade: 0,

    observacao: ""

});

    useEffect(() => {

        carregarProdutos();
	carregarSetores();


    }, []);

   async function carregarProdutos() {

    try {

        const response =
            await api.get("/api/produtos");

        setProdutos(response.data);

    } catch (erro) {

        console.error(erro);

    }
}
function alterarCampo(e) {

    const { name, value } = e.target;

    if (name === "produto") {

        setMovimentacao({

            ...movimentacao,

            produto: {
                id: Number(value)
            }

        });

        return;
    }

    if (name === "setor") {

        setMovimentacao({

            ...movimentacao,

            setor: {
                id: Number(value)
            }

        });

        return;
    }

    setMovimentacao({

        ...movimentacao,

        [name]:
            name === "quantidade"
                ? Number(value)
                : value

    });
}
async function salvar(e) {

    e.preventDefault();

    try {

        await api.post(
            "/api/movimentacoes",
            movimentacao
        );

        alert("Saída registrada com sucesso!");

        navigate("/movimentacoes");

    } catch (erro) {

    console.log("ERRO COMPLETO");
    console.log(erro);

    console.log("STATUS");
    console.log(erro.response?.status);

    console.log("BODY");
    console.log(erro.response?.data);

    alert("Erro ao registrar saída");
}
}	async function carregarSetores() {

    try {

        const response =
            await api.get("/api/setores");

        setSetores(response.data);

    } catch (erro) {

        console.error(erro);

    }
}
	
    return (

    <MainLayout>

        <PageHeader
    categoria="Movimentação"
    titulo="Saída de Estoque"
    subtitulo="Registre saídas de materiais."
    icone={<FaArrowUp color="#dc3545" size={24} />}
/>

        <form onSubmit={salvar}>

            <div className="mb-3 dashboard-fade-in ">

                <label>Produto</label>

               <select
   			 name="produto"
    			className="form-select"
    			value={movimentacao.produto.id}
   			 onChange={alterarCampo}
			>
   			 <option value="">
       			  Selecione um produto
   			 </option>

    			{produtos.map(produto => (

        		<option
            			key={produto.id}
            			value={produto.id}
       				 >
           			 {produto.nome}
        			</option>

   			 ))}
		</select>

            </div>

		<div className="mb-3 dashboard-fade-in">

    <label>Setor</label>

    <select
    name="setor"
    className="form-select"
    value={movimentacao.setor?.id || ""}
    onChange={alterarCampo}
>
    <option value="">
        Selecione um setor
    </option>

    {setores.map(setor => (

        <option
            key={setor.id}
            value={setor.id}
        >
            {setor.nome}
        </option>

    ))}
</select>

</div>	

	   
          
            <div className="mb-3 dashboard-fade-in">

                <label>Quantidade</label>

               <input
   			 type="number"
    			name="quantidade"
    			className="form-control"
    			value={movimentacao.quantidade}
    			onChange={alterarCampo}
		/>

            </div>

            <div className="mb-3 dashboard-fade-in">

                <label>Observação</label>

                <textarea
   			 name="observacao"
    			 className="form-control"
    			 rows="3"
    			 value={movimentacao.observacao}
    			 onChange={alterarCampo}
		/>

            </div>

            <button
    		type="submit"
    		className="btn btn-gold"
		>
   		 Registrar Saída
		</button>

        </form>

    </MainLayout>

);
}

export default NovaSaida;