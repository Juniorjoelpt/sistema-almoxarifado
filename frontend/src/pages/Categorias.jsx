import { useEffect, useState } from "react";
import MainLayout from "../layouts/MainLayout";
import api from "../services/api";
import AppCard from "../components/AppCard";
import PageHeader from "../components/PageHeader";
import { FaTags } from "react-icons/fa";



function Categorias() {

    const [categorias, setCategorias] = useState([]);
    const [nome, setNome] = useState("");
    const [showModal, setShowModal] = useState(false);
    const [idEdicao, setIdEdicao] = useState(null);

    useEffect(() => {
        carregarCategorias();
    }, []);

   async function carregarCategorias() {

        try {

            const response =
                await api.get("/api/categorias");

            setCategorias(response.data);

        } catch (erro) {

            console.error(
                "Erro ao carregar categorias",
                erro
            );
        }
    }

	function editarCategoria(categoria) {

    	setIdEdicao(categoria.id);

    	setNome(categoria.nome);

    	setShowModal(true);
	}
	async function salvarCategoria(e) {

    e.preventDefault();

    try {

        if (idEdicao) {

            await api.put(
                `/api/categorias/${idEdicao}`,
                {
                    nome
                }
            );

        } else {

            await api.post(
                "/api/categorias",
                {
                    nome
                }
            );
        }

        setNome("");
        setIdEdicao(null);

        setShowModal(false);

        carregarCategorias();

    } catch (erro) {

        console.error(
            "Erro ao salvar categoria",
            erro
        );
    }
}
async function excluirCategoria(id) {

    const confirmar =
        window.confirm(
            "Deseja excluir esta categoria?"
        );

    if (!confirmar) {
        return;
    }

    try {

        await api.delete(
            `/api/categorias/${id}`
        );

        carregarCategorias();

    } catch (erro) {

        console.error(
            "Erro ao excluir categoria",
            erro
        );
    }
}
	

    return (
	
	

        <MainLayout>

           <div className="d-flex justify-content-between mb-3 n ">

   		<PageHeader
    categoria="Cadastros"
    titulo="Categorias"
    subtitulo="Organize os produtos por categorias."
/>

    		<div className="d-flex align-items-start  ">
    		<button
        	className="btn btn-gold px-4"
        	onClick={() => setShowModal(true)}
    		>
       		 Nova Categoria
   		 </button>
		</div>

</div>
	
		 <div className="mt-3 dashboard-fade-in">
           <AppCard>

		<table className="table align-middle dashboard-fade-in">
                <thead>

                   	 <tr>

   			 <th>ID</th>
    			<th>Nome</th>
    			<th>Ações</th>

			</tr>

                </thead>

                <tbody>

                    {categorias.map(categoria => (

                        <tr key={categoria.id}>

                            <td>
                                {categoria.id}
                            </td>

                            <td>
    {categoria.nome}
</td>

<td>

    <button
        className="btn btn-warning btn-sm me-2"
        onClick={() =>
            editarCategoria(categoria)
        }
    >
        Editar
    </button>

    <button
        className="btn btn-danger btn-sm"
        onClick={() =>
            excluirCategoria(categoria.id)
        }
    >
        Excluir
    </button>

</td>

                        </tr>

                    ))}

                </tbody>

            </table>
</AppCard>
</div>

	{showModal && (
    <div className="modal d-block" tabIndex="-1">
        <div className="modal-dialog">
            <div className="modal-content">

                <div className="modal-header">
                    <h5>Nova Categoria</h5>

                    <button
                        className="btn-close"
                        onClick={() => setShowModal(false)}
                    />
                </div>

                <form onSubmit={salvarCategoria}>

                    <div className="modal-body">

                        <label>Nome</label>

                        <input
                            className="form-control"
                            value={nome}
                            onChange={(e) =>
                                setNome(e.target.value)
                            }
                        />

                    </div>

                    <div className="modal-footer">

                        <button
                            type="button"
                            className="btn btn-secondary"
                            onClick={() => setShowModal(false)}
                        >
                            Cancelar
                        </button>

                        <button
                            type="submit"
                            className="btn btn-primary"
                        >
                            Salvar
                        </button>

                    </div>

                </form>

            </div>
        </div>
    </div>
)}

        </MainLayout>

    );
}

export default Categorias;