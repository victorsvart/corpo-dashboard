export async function toData<T>(data: Response) {
  const serialization = await data.json();
  return serialization.data as T;
}
